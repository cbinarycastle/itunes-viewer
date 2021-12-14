package com.test.bountifarm.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.test.bountifarm.databinding.ItemLoadStateBinding

class DefaultLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<LoadStateViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): LoadStateViewHolder {
        return ItemLoadStateBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ).let {
            LoadStateViewHolder(it, retry)
        }
    }

    override fun onBindViewHolder(
        holder: LoadStateViewHolder,
        loadState: LoadState
    ) = holder.bind(loadState)

    override fun displayLoadStateAsItem(loadState: LoadState): Boolean {
        return super.displayLoadStateAsItem(loadState)
                || (loadState is LoadState.NotLoading && loadState.endOfPaginationReached)
    }
}
