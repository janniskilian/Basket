package de.janniskilian.basket.core.util.extension.extern

import android.widget.ImageView

fun ImageView.setSelectedImageState(isSelected: Boolean) {
    setImageState(createSelectedStateSet(isSelected), true)
}

private fun createSelectedStateSet(isSelected: Boolean): IntArray {
    val value = if (isSelected) 1 else -1
    return intArrayOf(android.R.attr.state_selected * value)
}
