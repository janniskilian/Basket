package de.janniskilian.basket.core.util.android

import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment

fun Fragment.hideKeyboard() {
    activity?.let {
        WindowInsetsControllerCompat(
            it.window,
            it.findViewById(android.R.id.content)
        ).hide(WindowInsetsCompat.Type.ime())
    }
}
