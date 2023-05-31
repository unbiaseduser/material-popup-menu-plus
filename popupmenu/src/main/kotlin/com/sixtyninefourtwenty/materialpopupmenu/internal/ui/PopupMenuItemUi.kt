package com.sixtyninefourtwenty.materialpopupmenu.internal.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sixtyninefourtwenty.materialpopupmenu.MaterialPopupMenu
import com.sixtyninefourtwenty.materialpopupmenu.databinding.MpmItemBinding
import com.sixtyninefourtwenty.materialpopupmenu.internal.MenuNavStack

internal class PopupMenuItemUi(context: Context, parent: ViewGroup) {
    private val binding = MpmItemBinding.inflate(LayoutInflater.from(context), parent, false)
    private val icon = binding.icon
    private val label = binding.label
    private val nestedIcon = binding.nestedIcon
    val root = binding.root

    fun bind(item: MaterialPopupMenu.PopupMenuItem) {
        val data = item.data
        val menu = data.subMenu
        item.bindToViews(icon, label)
        if (menu != null) {
            nestedIcon.visibility = View.VISIBLE
            root.setOnClickListener {
                data.onSelectListener?.run()
                MenuNavStack.navigateForward(menu)
            }
        } else {
            root.setOnClickListener {
                data.onSelectListener?.run()
                item.dismissMenuIfAllowed()
            }
        }
    }
}