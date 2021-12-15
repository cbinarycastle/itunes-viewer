package com.test.bountifarm.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.test.bountifarm.R
import com.test.bountifarm.databinding.FragmentMusicListBinding
import com.test.bountifarm.ui.SearchFragment.Companion.RESULT_KEY_QUERY
import com.test.bountifarm.util.launchAndRepeatWithViewLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
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
        adapter = MusicAdapter()
        binding = FragmentMusicListBinding.inflate(
            inflater, container, false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@MusicListFragment.viewModel
            errorEventListener = object : ErrorEventListener {
                override fun retry() = adapter.retry()
            }
        }
        return binding.root
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

        binding.recyclerView.run {
            adapter = this@MusicListFragment.adapter.withLoadStateFooter(
                createLoadStateAdapter()
            )
            addItemDecoration(
                DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            )
        }

        navController.currentBackStackEntry
            ?.savedStateHandle
            ?.getLiveData<String>(RESULT_KEY_QUERY)
            ?.observe(viewLifecycleOwner) { viewModel.searchMusics(it) }

        launchAndRepeatWithViewLifecycle {
            launch {
                viewModel.musics.collectLatest {
                    adapter.submitData(it)
                }
            }

            launch {
                adapter.loadStateFlow
                    .distinctUntilChangedBy { it.refresh }
                    .map { it.refresh }
                    .collectLatest {
                        viewModel.onRefreshLoadStateChanged(
                            loadState = it,
                            itemCount = adapter.itemCount
                        )
                    }
            }

            launch {
                viewModel.scrollTopEvent.collectLatest {
                    scrollToTop()
                }
            }
        }
    }

    private fun scrollToTop() {
        binding.recyclerView.scrollToPosition(0)
    }
}