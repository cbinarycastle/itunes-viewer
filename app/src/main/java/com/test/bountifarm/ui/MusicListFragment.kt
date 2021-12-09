package com.test.bountifarm.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.test.bountifarm.R
import com.test.bountifarm.databinding.FragmentMusicListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MusicListFragment : Fragment() {

    private lateinit var binding: FragmentMusicListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMusicListBinding.inflate(inflater, container, false)
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
            NavigationUI.setupWithNavController(binding.toolbar, navController)
        }
    }
}