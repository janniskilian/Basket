package de.janniskilian.basket.core.util.android.view

import androidx.appcompat.widget.Toolbar
import androidx.core.view.updateLayoutParams
import com.google.android.material.appbar.AppBarLayout

fun Toolbar.setScrollable(isScrollable: Boolean) {
    updateLayoutParams<AppBarLayout.LayoutParams> {
        scrollFlags = if (isScrollable) {
            AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or
                    AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
        } else {
            0
        }
    }
}
