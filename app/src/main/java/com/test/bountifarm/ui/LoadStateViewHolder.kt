package com.test.bountifarm.ui

import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.test.bountifarm.databinding.ItemLoadStateBinding

class LoadStateViewHolder(
    private val binding: ItemLoadStateBinding,
    private val retry: () -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.retryButton.setOnClickListener {
            retry()
        }
    }

    fun bind(loadState: LoadState) {
        binding.progressBar.isVisible = loadState == LoadState.Loading
        binding.errorMessage.isVisible = loadState is LoadState.Error
        binding.retryButton.isVisible = loadState is LoadState.Error

        if (loadState is LoadState.Error) {
            binding.errorMessage.text = loadState.error.message
        }
    }
}