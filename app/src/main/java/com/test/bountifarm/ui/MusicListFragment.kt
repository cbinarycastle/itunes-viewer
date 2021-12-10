package com.test.bountifarm.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.test.bountifarm.R
import com.test.bountifarm.databinding.FragmentMusicListBinding
import com.test.bountifarm.ui.SearchFragment.Companion.RESULT_KEY_QUERY
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MusicListFragment : Fragment() {

    private val viewModel by viewModels<MusicListViewModel>()
    private lateinit var binding: FragmentMusicListBinding
    private lateinit var adapter: MusicAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMusicListBinding.inflate(inflater, container, false)
        adapter = MusicAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()

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

        findNavController().currentBackStackEntry
            ?.savedStateHandle
            ?.getLiveData<String>(RESULT_KEY_QUERY)
            ?.observe(viewLifecycleOwner) {
                viewModel.searchMusics(it)
            }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.musics.collect {
                    adapter.submitData(it)
                }
            }
        }
    }
}