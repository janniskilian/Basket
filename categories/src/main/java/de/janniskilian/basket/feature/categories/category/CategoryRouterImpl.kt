package de.janniskilian.basket.feature.categories.category

import kotlinx.android.synthetic.main.fragment_category.*

class CategoryRouterImpl(private val fragment: CategoryFragment) : CategoryRouter {

    override fun dismiss() {
        fragment.nameEditText.clearFocus()
        fragment.dismiss()
    }
}
