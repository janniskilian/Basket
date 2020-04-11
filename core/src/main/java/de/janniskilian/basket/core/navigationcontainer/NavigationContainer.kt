package de.janniskilian.basket.core.navigationcontainer

import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

interface NavigationContainer {

    fun showSnackbar(@StringRes resId: Int, duration: Int, configure: Snackbar.() -> Unit)

    fun dismissSnackbar()

    fun attachSearchBar(viewModel: SearchBarViewModel)
}
