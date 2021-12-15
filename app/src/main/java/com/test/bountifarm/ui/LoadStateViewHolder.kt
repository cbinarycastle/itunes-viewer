package com.test.bountifarm.ui

import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.test.bountifarm.databinding.ItemLoadStateBinding
import java.util.*

class LoadStateViewHolder(
    private val binding: ItemLoadStateBinding,
    private val retry: () -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.errorView.retryButton.setOnClickListener {
            retry()
        }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.errorMessage = loadState.error.message
        }

        binding.errorEventListener = object : ErrorEventListener {
            override fun retry() {
                this@LoadStateViewHolder.retry()
            }
        }

        binding.progressBar.isVisible = loadState == LoadState.Loading
        binding.errorView.root.isVisible = loadState is LoadState.Error
        binding.noMoreResultText.isVisible =
            loadState is LoadState.NotLoading && loadState.endOfPaginationReached
    }
}