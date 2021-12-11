package com.test.bountifarm.ui

import androidx.recyclerview.widget.RecyclerView
import com.test.bountifarm.databinding.ItemMusicBinding
import com.test.bountifarm.domain.Music

class MusicViewHolder(
    private val binding: ItemMusicBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(music: Music) {
        binding.music = music
    }
}