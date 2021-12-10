package com.test.bountifarm.ui

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.test.bountifarm.domain.Music

class MusicAdapter : PagingDataAdapter<Music, MusicViewHolder>(MusicDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}

internal class MusicDiffCallback : DiffUtil.ItemCallback<Music>() {

    override fun areItemsTheSame(oldItem: Music, newItem: Music): Boolean {
        TODO("Not yet implemented")
    }

    override fun areContentsTheSame(oldItem: Music, newItem: Music): Boolean {
        TODO("Not yet implemented")
    }
}