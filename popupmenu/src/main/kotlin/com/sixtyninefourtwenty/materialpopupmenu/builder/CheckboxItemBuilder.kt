package com.sixtyninefourtwenty.materialpopupmenu.builder

import androidx.annotation.StringRes
import com.sixtyninefourtwenty.materialpopupmenu.MaterialPopupMenu
import com.sixtyninefourtwenty.materialpopupmenu.MaterialPopupMenuBuilder

/**
 * Builder class to create a [MaterialPopupMenu.PopupMenuCheckboxItem].
 */
class CheckboxItemBuilder private constructor(override val data: MaterialPopupMenuBuilder.CheckboxItem.Data) :
    ToggleItemBuilder<CheckboxItemBuilder>() {
    constructor(label: CharSequence) : this(MaterialPopupMenuBuilder.CheckboxItem.Data(label))
    constructor(@StringRes labelRes: Int) : this(MaterialPopupMenuBuilder.CheckboxItem.Data(labelRes))

    override fun self() = this
    override fun build(): MaterialPopupMenu.PopupMenuCheckboxItem =
        MaterialPopupMenu.PopupMenuCheckboxItem(data, resolveOnShowCallback())
}