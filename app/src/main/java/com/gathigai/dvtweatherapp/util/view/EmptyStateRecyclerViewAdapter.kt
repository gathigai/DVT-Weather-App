package com.gathigai.dvtweatherapp.util.view

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class EmptyStateRecyclerViewAdapter<T, VH : RecyclerView.ViewHolder>(val emptyView: View? = null, val listView: View, diffCallBack: DiffUtil.ItemCallback<T>) : ListAdapter<T, VH>(diffCallBack) {
    override fun submitList(list: List<T>?) {
        super.submitList(list)
        if (list == null || list.isEmpty()) {
            emptyView?.visibility = View.VISIBLE
            listView.visibility = View.GONE
        } else {
            emptyView?.visibility = View.GONE
            listView.visibility = View.VISIBLE
        }
    }

    interface OnItemClickedListener<T> {
        fun onItemClicked(item: T)
    }
}