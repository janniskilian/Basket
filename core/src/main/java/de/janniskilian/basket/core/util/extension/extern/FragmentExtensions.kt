package de.janniskilian.basket.core.util.extension.extern

import androidx.fragment.app.Fragment

fun Fragment.hideKeyboard() {
    activity?.hideKeyboard()
}
