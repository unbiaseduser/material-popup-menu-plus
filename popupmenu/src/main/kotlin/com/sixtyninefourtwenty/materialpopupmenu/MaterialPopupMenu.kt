package com.sixtyninefourtwenty.materialpopupmenu

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.view.View
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.StyleRes
import androidx.annotation.UiThread
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.widget.ImageViewCompat
import com.sixtyninefourtwenty.materialpopupmenu.builder.SectionBuilder
import com.sixtyninefourtwenty.materialpopupmenu.internal.MaterialRecyclerViewPopupWindow
import com.sixtyninefourtwenty.materialpopupmenu.internal.MenuNavStack
import com.sixtyninefourtwenty.materialpopupmenu.internal.PopupMenuAdapter

/**
 * Holds all the required information for showing a popup menu.
 *
 * @param data container of the information
 * @param sections a list of sections
 *
 * @author Piotr Zawadzki
 */
class MaterialPopupMenu
internal constructor(
    private val view: View,
    private val context: Context,
    private val data: MaterialPopupMenuBuilder.Data,
    private val sections: MutableList<PopupMenuSection>
) {

    private var popupWindow: MaterialRecyclerViewPopupWindow? = null

    private var dismissListener: Runnable? = null

    internal fun isRootMenu() = !data.isSubMenu

    internal fun setIsSubMenu(
        labelRes: Int = 0,
        label: CharSequence? = null,
        navBackConfig: MaterialPopupMenuBuilder.NavBackItem.() -> Unit
    ) = apply {
        data.isSubMenu = true
        sections.add(0, MaterialPopupMenuBuilder.Section(context).apply {
            navBackItem(labelRes, label, navBackConfig)
        }.toPopupMenuSection())
    }

    internal fun setIsSubMenu(navBackItem: PopupMenuNavBackItem) = apply {
        data.isSubMenu = true
        sections.add(0, SectionBuilder().addNavBackItem(navBackItem).build())
    }

    /**
     * Shows a popup menu in the UI.
     */
    @UiThread
    fun show() {
        MenuNavStack.init(this)
        val popupWindow = MaterialRecyclerViewPopupWindow(
            view = view,
            adapter = PopupMenuAdapter(sections) { popupWindow?.dismiss() },
            context = ContextThemeWrapper(context, resolvePopupStyle()),
            dropDownGravity = data.dropdownGravity,
            fixedContentWidthInPx = data.fixedContentWidthInPx,
            dropDownVerticalOffset = data.dropDownVerticalOffset,
            dropDownHorizontalOffset = data.dropDownHorizontalOffset,
            customAnimation = data.customAnimation
        )

        popupWindow.show()
        this.popupWindow = popupWindow
        setOnDismissListener(dismissListener)
    }

    /**
     * Dismisses the popup window.
     */
    @UiThread
    fun dismiss() = popupWindow?.dismiss()

    /**
     * Sets a listener that is called when this popup window is dismissed.
     */
    fun setOnDismissListener(listener: Runnable?) {
        dismissListener = listener
        popupWindow?.setOnDismissListener(listener)
    }

    @StyleRes
    private fun resolvePopupStyle(): Int {
        if (data.style != 0)
            return data.style

        val a: TypedArray =
            context.obtainStyledAttributes(intArrayOf(R.attr.materialPopupMenuStyle))
        val resolvedStyle = a.getResourceId(0, R.style.Widget_MPM_Menu)
        a.recycle()

        return resolvedStyle
    }

    data class PopupMenuSection internal constructor(
        internal val data: MaterialPopupMenuBuilder.Section.Data,
        internal val items: List<AbstractPopupMenuItem>
    )

    sealed class PopupMenuNormalItem(
        override val data: MaterialPopupMenuBuilder.NormalItem.Data,
        override val onShowCallback: OnShowCallback
    ) : AbstractPopupMenuItem(data, onShowCallback) {
        fun bindToViews(icon: ImageView, label: TextView) {
            if (data.label != null) {
                label.text = data.label
            } else {
                label.setText(data.labelRes)
            }
            label.gravity = data.labelAlignment
            label.typeface = data.labelTypeface
            if (data.icon != 0 || data.iconDrawable != null) {
                with(icon) {
                    visibility = View.VISIBLE
                    when {
                        data.icon != 0 -> setImageResource(data.icon)
                        data.iconDrawable != null -> setImageDrawable(data.iconDrawable)
                    }
                    if (data.iconColor != 0) {
                        ImageViewCompat.setImageTintList(
                            this,
                            ColorStateList.valueOf(data.iconColor)
                        )
                    }
                }
            }
            if (data.labelColor != 0) {
                label.setTextColor(data.labelColor)
            }
        }
    }

    sealed class PopupMenuToggleItem(
        override val data: MaterialPopupMenuBuilder.ToggleItem.Data,
        override val onShowCallback: OnShowCallback
    ) : PopupMenuNormalItem(data, onShowCallback) {
        fun bindToViews(icon: ImageView, label: TextView, toggle: CompoundButton) {
            bindToViews(icon, label)
            toggle.isChecked = data.isChecked
            data.config?.accept(onShowCallback, toggle)
        }
    }

    data class PopupMenuItem internal constructor(
        override val data: MaterialPopupMenuBuilder.Item.Data,
        override val onShowCallback: OnShowCallback
    ) : PopupMenuNormalItem(data, onShowCallback)

    data class PopupMenuCheckboxItem internal constructor(
        override val data: MaterialPopupMenuBuilder.CheckboxItem.Data,
        override val onShowCallback: OnShowCallback
    ) : PopupMenuToggleItem(data, onShowCallback)

    data class PopupMenuSwitchItem internal constructor(
        override val data: MaterialPopupMenuBuilder.SwitchItem.Data,
        override val onShowCallback: OnShowCallback
    ) : PopupMenuToggleItem(data, onShowCallback)

    data class PopupMenuCustomItem internal constructor(
        override val data: MaterialPopupMenuBuilder.CustomItem.Data,
        override val onShowCallback: OnShowCallback
    ) : AbstractPopupMenuItem(data, onShowCallback)

    data class PopupMenuRadioGroupItem internal constructor(
        override val data: MaterialPopupMenuBuilder.RadioGroupItem.Data,
        override val onShowCallback: OnShowCallback
    ) : AbstractPopupMenuItem(data, onShowCallback)

    data class PopupMenuRadioButtonItem internal constructor(
        override val data: MaterialPopupMenuBuilder.RadioButtonItem.Data,
        override val onShowCallback: OnShowCallback
    ) : PopupMenuToggleItem(data, onShowCallback)

    data class PopupMenuNavBackItem internal constructor(
        override val data: MaterialPopupMenuBuilder.NavBackItem.Data,
        override val onShowCallback: OnShowCallback
    ) : PopupMenuNormalItem(data, onShowCallback)

    sealed class AbstractPopupMenuItem(
        internal open val data: MaterialPopupMenuBuilder.AbstractItem.Data,
        internal open val onShowCallback: OnShowCallback
    ) {
        internal fun dismissMenuIfAllowed() {
            if (data.dismissOnSelect) {
                onShowCallback.dismissPopupAction()
            }
        }
    }
}
