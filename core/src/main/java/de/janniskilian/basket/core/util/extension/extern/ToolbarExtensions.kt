package de.janniskilian.basket.core.util.extension.extern

import androidx.appcompat.widget.Toolbar
import androidx.core.view.updateLayoutParams
import com.google.android.material.appbar.AppBarLayout

fun Toolbar.setScrollable(scrollable: Boolean) {
    updateLayoutParams<AppBarLayout.LayoutParams> {
        scrollFlags = if (scrollable) {
            AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
        } else {
            0
        }
    }
}
