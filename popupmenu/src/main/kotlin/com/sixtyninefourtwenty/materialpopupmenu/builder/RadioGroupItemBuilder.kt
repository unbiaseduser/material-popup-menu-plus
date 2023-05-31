package com.sixtyninefourtwenty.materialpopupmenu.builder

import com.sixtyninefourtwenty.materialpopupmenu.MaterialPopupMenu
import com.sixtyninefourtwenty.materialpopupmenu.MaterialPopupMenuBuilder

class RadioGroupItemBuilder : AbstractItemBuilder<RadioGroupItemBuilder>() {
    override val data = MaterialPopupMenuBuilder.RadioGroupItem.Data()
    fun addItem(item: MaterialPopupMenu.PopupMenuRadioButtonItem) =
        apply { data.radioButtonItems.add(item) }

    override fun self() = this
    override fun build(): MaterialPopupMenu.PopupMenuRadioGroupItem {
        require(data.radioButtonItems.size >= 2) { "Radio groups must have 2 or more items" }
        return MaterialPopupMenu.PopupMenuRadioGroupItem(data, resolveOnShowCallback())
    }
}