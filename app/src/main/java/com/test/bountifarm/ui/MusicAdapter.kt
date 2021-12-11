package com.test.bountifarm.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.test.bountifarm.databinding.ItemMusicBinding
import com.test.bountifarm.domain.Music

class MusicAdapter : PagingDataAdapter<Music, MusicViewHolder>(MusicDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        return ItemMusicBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ).let {
            MusicViewHolder(it)
        }
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }
}

internal class MusicDiffCallback : DiffUtil.ItemCallback<Music>() {

    override fun areItemsTheSame(oldItem: Music, newItem: Music): Boolean {
        return oldItem.trackId == newItem.trackId
    }

    override fun areContentsTheSame(oldItem: Music, newItem: Music): Boolean {
        return oldItem == newItem
    }
}