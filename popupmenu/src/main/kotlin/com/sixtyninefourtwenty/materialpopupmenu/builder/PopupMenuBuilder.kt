package com.sixtyninefourtwenty.materialpopupmenu.builder

import android.content.Context
import android.view.View
import androidx.annotation.GravityInt
import androidx.annotation.Px
import androidx.annotation.StyleRes
import com.sixtyninefourtwenty.materialpopupmenu.MaterialPopupMenu
import com.sixtyninefourtwenty.materialpopupmenu.MaterialPopupMenuBuilder
import com.sixtyninefourtwenty.materialpopupmenu.PopupAnimation

class PopupMenuBuilder(private val context: Context, private val view: View) {
    private val data = MaterialPopupMenuBuilder.Data()
    private val sectionList = mutableListOf<MaterialPopupMenu.PopupMenuSection>()

    /**
     * Use the [SectionBuilder] class to build the section.
     */
    fun addSection(section: MaterialPopupMenu.PopupMenuSection) = apply { sectionList.add(section) }
    fun setStyle(@StyleRes style: Int) = apply { data.style = style }
    fun setDropdownGravity(@GravityInt gravity: Int) = apply { data.dropdownGravity = gravity }
    fun setFixedContentWidth(@Px px: Int) = apply { data.fixedContentWidthInPx = px }
    fun setDropdownVerticalOffset(offset: Int) = apply { data.dropDownVerticalOffset = offset }
    fun setDropdownHorizontalOffset(offset: Int) = apply { data.dropDownHorizontalOffset = offset }
    fun setCustomAnimation(anim: PopupAnimation) = apply { data.customAnimation = anim }

    fun build(): MaterialPopupMenu {
        require(sectionList.isNotEmpty()) { "Popup menu sections cannot be empty!" }
        return MaterialPopupMenu(
            view = view,
            context = context,
            data = data,
            sections = sectionList.filterTo(arrayListOf()) { !it.data.shouldBeHidden }
        )
    }
}