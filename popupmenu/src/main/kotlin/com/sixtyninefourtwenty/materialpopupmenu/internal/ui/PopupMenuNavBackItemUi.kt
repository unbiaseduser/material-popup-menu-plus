package com.sixtyninefourtwenty.materialpopupmenu.internal.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.sixtyninefourtwenty.materialpopupmenu.MaterialPopupMenu
import com.sixtyninefourtwenty.materialpopupmenu.databinding.MpmItemBinding
import com.sixtyninefourtwenty.materialpopupmenu.internal.MenuNavStack

internal class PopupMenuNavBackItemUi(context: Context, parent: ViewGroup) {
    private val binding = MpmItemBinding.inflate(LayoutInflater.from(context), parent, false)
    private val icon = binding.icon
    private val label = binding.label
    val root = binding.root

    internal fun bind(item: MaterialPopupMenu.PopupMenuNavBackItem) {
        val data = item.data
        item.bindToViews(icon, label)
        root.setOnClickListener {
            data.onSelectListener?.run()
            MenuNavStack.navigateBackwards()
        }
    }
}