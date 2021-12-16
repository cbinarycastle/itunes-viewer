package com.test.bountifarm.util

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

inline fun Fragment.launchAndRepeatWithViewLifecycle(
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline block: suspend CoroutineScope.() -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(minActiveState) {
            block()
        }
    }
}

fun RecyclerView.anchorSmoothScrollToPosition(position: Int, anchorPosition: Int = 10) {
    layoutManager?.apply {
        when (this) {
            is LinearLayoutManager -> {
                val topItem = findFirstVisibleItemPosition()
                val distance = topItem - position
                val anchorItem = when {
                    distance > anchorPosition -> position + anchorPosition
                    distance < -anchorPosition -> position - anchorPosition
                    else -> topItem
                }
                if (anchorItem != topItem) {
                    scrollToPosition(anchorItem)
                }
                post {
                    smoothScrollToPosition(position)
                }
            }
            else -> smoothScrollToPosition(position)
        }
    }
}