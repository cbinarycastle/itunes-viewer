package com.test.bountifarm.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.paging.LoadState
import com.test.bountifarm.R
import com.test.bountifarm.databinding.FragmentMusicListBinding
import com.test.bountifarm.ui.SearchFragment.Companion.RESULT_KEY_QUERY
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MusicListFragment : Fragment() {

    private val viewModel by viewModels<MusicListViewModel>()
    private lateinit var binding: FragmentMusicListBinding
    private lateinit var adapter: MusicAdapter
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMusicListBinding.inflate(inflater, container, false)
        adapter = MusicAdapter()
        return binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = viewModel
            recyclerView.adapter = adapter.withLoadStateFooter(
                createLoadStateAdapter()
            )
        }.root
    }

    private fun createLoadStateAdapter() = DefaultLoadStateAdapter { adapter.retry() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController()

        binding.toolbar.run {
            inflateMenu(R.menu.menu_music_list)
            setOnMenuItemClickListener {
                if (it.itemId == R.id.action_search) {
                    navController.navigate(MusicListFragmentDirections.toSearch())
                    true
                } else {
                    false
                }
            }
            setupWithNavController(navController)
        }

        navController.currentBackStackEntry
            ?.savedStateHandle
            ?.getLiveData<String>(RESULT_KEY_QUERY)
            ?.observe(viewLifecycleOwner) {
                viewModel.searchMusics(it)
            }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.errorMessage.collect {
                        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                    }
                }

                launch {
                    viewModel.musics.collectLatest {
                        adapter.submitData(it)
                    }
                }

                launch {
                    adapter.loadStateFlow
                        .distinctUntilChanged { old, new ->
                            old.refresh == new.refresh
                        }
                        .collectLatest {
                            when (it.refresh) {
                                is LoadState.NotLoading -> {
                                    viewModel.setInitialLoading(false)
                                    scrollToTop()
                                }
                                is LoadState.Error -> TODO()
                                LoadState.Loading -> viewModel.setInitialLoading(true)
                            }
                        }
                }
            }
        }
    }

    private fun scrollToTop() {
        binding.recyclerView.scrollToPosition(0)
    }
}