package de.janniskilian.basket.core.util.extension.extern

import android.widget.ImageView

fun ImageView.setSelectedImageState(selected: Boolean) {
    setImageState(createSelectedStateSet(selected), true)
}

private fun createSelectedStateSet(selected: Boolean): IntArray {
    val value = if (selected) 1 else -1
    return intArrayOf(android.R.attr.state_selected * value)
}
