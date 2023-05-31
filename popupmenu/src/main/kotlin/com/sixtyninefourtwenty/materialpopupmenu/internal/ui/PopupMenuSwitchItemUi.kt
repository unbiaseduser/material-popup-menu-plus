package com.sixtyninefourtwenty.materialpopupmenu.internal.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.SwitchCompat
import com.google.android.material.materialswitch.MaterialSwitch
import com.google.android.material.switchmaterial.SwitchMaterial
import com.sixtyninefourtwenty.materialpopupmenu.MaterialPopupMenu
import com.sixtyninefourtwenty.materialpopupmenu.R
import com.sixtyninefourtwenty.materialpopupmenu.databinding.MpmSwitchItemBinding

internal class PopupMenuSwitchItemUi(context: Context, parent: ViewGroup) {
    private val binding = MpmSwitchItemBinding.inflate(LayoutInflater.from(context), parent, false)
    private val icon = binding.icon
    private val label = binding.label
    private val switch: SwitchCompat
    val root = binding.root

    /*
    M3's MaterialSwitch causes crashes on M2 themes, so we manually create the switch depending on whether the theme we're running on is M3
     */
    init {
        val a = context.obtainStyledAttributes(intArrayOf(R.attr.mpm_isM3))
        val isM3 = a.getBoolean(0, true)
        switch = if (isM3) MaterialSwitch(context) else SwitchMaterial(context)
        with(switch) {
            isClickable = false
            background = null
        }
        binding.switchContainer.addView(switch)
        a.recycle()
    }

    fun bind(item: MaterialPopupMenu.PopupMenuSwitchItem) {
        val data = item.data
        item.bindToViews(icon, label, switch)
        root.setOnClickListener {
            data.onSelectListener?.run()
            if (data.isItemEligibleForToggling(switch))
                switch.toggle()
            item.dismissMenuIfAllowed()
        }
    }
}