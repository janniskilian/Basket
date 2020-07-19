package de.janniskilian.basket.core.util.extension.extern

import androidx.fragment.app.Fragment
import de.janniskilian.basket.core.BasketApp
import de.janniskilian.basket.core.module.AppModule

val Fragment.app: BasketApp
    get() = requireActivity().application as BasketApp

val Fragment.appModule: AppModule
    get() = app.appModule

fun Fragment.hideKeyboard() {
    activity?.hideKeyboard()
}
