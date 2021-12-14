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
        binding.errorLayout.retryButton.setOnClickListener {
            retry()
        }
    }

    fun bind(loadState: LoadState) {
        binding.progressBar.isVisible = loadState == LoadState.Loading
        binding.errorLayout.errorMessage.isVisible = loadState is LoadState.Error
        binding.errorLayout.retryButton.isVisible = loadState is LoadState.Error
        binding.noMoreResultText.isVisible =
            loadState is LoadState.NotLoading && loadState.endOfPaginationReached

        if (loadState is LoadState.Error) {
            binding.errorLayout.errorMessage.text = loadState.error.message
        }
    }
}