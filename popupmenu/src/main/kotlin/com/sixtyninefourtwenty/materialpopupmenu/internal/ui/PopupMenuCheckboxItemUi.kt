package com.sixtyninefourtwenty.materialpopupmenu.internal.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.sixtyninefourtwenty.materialpopupmenu.MaterialPopupMenu
import com.sixtyninefourtwenty.materialpopupmenu.databinding.MpmCheckboxItemBinding

internal class PopupMenuCheckboxItemUi(context: Context, parent: ViewGroup) {
    private val binding = MpmCheckboxItemBinding.inflate(LayoutInflater.from(context), parent, false)
    private val icon = binding.icon
    private val label = binding.label
    private val checkbox = binding.checkbox
    val root = binding.root

    fun bind(item: MaterialPopupMenu.PopupMenuCheckboxItem) {
        val data = item.data
        item.bindToViews(icon, label, checkbox)
        root.setOnClickListener {
            data.onSelectListener?.run()
            if (data.isItemEligibleForToggling(checkbox))
                checkbox.toggle()
            item.dismissMenuIfAllowed()
        }
    }
}