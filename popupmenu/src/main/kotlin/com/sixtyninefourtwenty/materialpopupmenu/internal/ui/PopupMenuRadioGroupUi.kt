package com.sixtyninefourtwenty.materialpopupmenu.internal.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.LinearLayout
import android.widget.RadioButton
import com.sixtyninefourtwenty.materialpopupmenu.MaterialPopupMenu
import com.sixtyninefourtwenty.materialpopupmenu.databinding.MpmRadioButtonItemBinding

internal class PopupMenuRadioGroupUi(private val context: Context) {

    internal class NestedRadioGroup @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
    ) : LinearLayout(context, attrs, defStyleAttr) {

        private val radioButtons = mutableListOf<RadioButton>()
        var items: List<MaterialPopupMenu.PopupMenuRadioButtonItem>? = null

        init {
            viewTreeObserver.addOnGlobalLayoutListener(object :
                ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    viewTreeObserver.removeOnGlobalLayoutListener(this)
                    setupChildren()
                }
            })
        }

        fun setupChildren() {
            for (i in 0 until childCount) {
                val child = getChildAt(i)
                radioButtons.add(findRadioButtonOnChild(child))
                val finalItems = items
                if (finalItems == null) {
                    child.setOnClickListener {
                        checkRadioButton(radioButtons[i])
                    }
                } else {
                    child.setOnClickListener {
                        with(finalItems[i]) {
                            data.onSelectListener?.run()
                            if (!radioButtons[i].isChecked && data.isItemEligibleForToggling(radioButtons[i])) {
                                checkRadioButton(radioButtons[i])
                            }
                            dismissMenuIfAllowed()
                        }
                    }
                }
            }
            //guard against multiple radio buttons being initially checked
            for (button in radioButtons) {
                if (button.isChecked) {
                    radioButtons.forEach { if (it !== button) it.isChecked = false }
                    break
                }
            }
        }

        private fun checkRadioButton(rb: RadioButton) {
            if (!rb.isChecked) {
                rb.isChecked = true
                radioButtons.forEach { if (it !== rb) it.isChecked = false }
            }
        }

        private fun findRadioButtonOnChild(child: View): RadioButton {
            if (child is ViewGroup) {
                for (i in 0 until child.childCount) {
                    val view = child.getChildAt(i)
                    if (view is RadioButton)
                        return view
                    if (view is ViewGroup)
                        findRadioButtonOnChild(view)
                }
            }
            throw IllegalStateException("You need to add radio button inside of NestedRadioGroup")
        }

    }

    val root = NestedRadioGroup(context).apply {
        orientation = LinearLayout.VERTICAL
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    fun bindItems(items: List<MaterialPopupMenu.PopupMenuRadioButtonItem>, dismissPopupCallback: () -> Unit) {
        items.forEach { item ->
            root.addView(PopupMenuRadioButtonUi(context, root).apply {
                bind(item, dismissPopupCallback)
            }.root)
        }
        root.items = items
    }

    private class PopupMenuRadioButtonUi(context: Context, parent: ViewGroup) {
        private val binding = MpmRadioButtonItemBinding.inflate(LayoutInflater.from(context), parent, false)
        private val icon = binding.icon
        private val label = binding.label
        private val radioButton = binding.radioButton
        val root = binding.root

        fun bind(item: MaterialPopupMenu.PopupMenuRadioButtonItem, dismissPopupCallback: () -> Unit) {
            item.onShowCallback.dismissPopupAction = dismissPopupCallback
            item.bindToViews(icon, label, radioButton)
        }
    }
}