package com.test.bountifarm.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.paging.LoadState
import com.test.bountifarm.R
import com.test.bountifarm.databinding.FragmentMusicListBinding
import com.test.bountifarm.ui.SearchFragment.Companion.RESULT_KEY_QUERY
import com.test.bountifarm.util.launchAndRepeatWithViewLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.map

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
            recyclerView.adapter = adapter.withLoadStateFooter(
                createLoadStateAdapter()
            )
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

        navController.currentBackStackEntry
            ?.savedStateHandle
            ?.getLiveData<String>(RESULT_KEY_QUERY)
            ?.observe(viewLifecycleOwner) { viewModel.searchMusics(it) }

        launchAndRepeatWithViewLifecycle {
            viewModel.musics.collectLatest {
                adapter.submitData(it)
            }
        }

        launchAndRepeatWithViewLifecycle {
            adapter.loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .map { it.refresh }
                .collectLatest {
                    binding.progressBar.isVisible = it == LoadState.Loading
                    binding.errorLayout.root.isVisible = it is LoadState.Error
                    binding.emptyResultText.isVisible =
                        it is LoadState.NotLoading && adapter.itemCount == 0

                    if (it is LoadState.NotLoading) {
                        scrollToTop()
                    }

                    if (it is LoadState.Error) {
                        binding.errorLayout.run {
                            errorMessage.text = it.error.message
                            retryButton.setOnClickListener { adapter.retry() }
                        }
                    }
                }
        }
    }

    private fun scrollToTop() {
        binding.recyclerView.scrollToPosition(0)
    }
}