@file:JvmSynthetic

package androidx.appcompat.widget

import android.content.Context
import android.widget.PopupWindow
import com.sixtyninefourtwenty.materialpopupmenu.PopupAnimation

internal fun createAppCompatPopupWindow(
    context: Context,
    customAnimation: PopupAnimation?
): PopupWindow = MaterialPopupWindow(context, customAnimation)
