package com.sixtyninefourtwenty.materialpopupmenu.builder

import androidx.annotation.StringRes
import com.sixtyninefourtwenty.materialpopupmenu.MaterialPopupMenu
import com.sixtyninefourtwenty.materialpopupmenu.MaterialPopupMenuBuilder

class SwitchItemBuilder private constructor(override val data: MaterialPopupMenuBuilder.SwitchItem.Data) :
    ToggleItemBuilder<SwitchItemBuilder>() {
    constructor(label: CharSequence) : this(MaterialPopupMenuBuilder.SwitchItem.Data(label))
    constructor(@StringRes labelRes: Int) : this(MaterialPopupMenuBuilder.SwitchItem.Data(labelRes))

    override fun self() = this
    override fun build(): MaterialPopupMenu.PopupMenuSwitchItem =
        MaterialPopupMenu.PopupMenuSwitchItem(data, resolveOnShowCallback())
}