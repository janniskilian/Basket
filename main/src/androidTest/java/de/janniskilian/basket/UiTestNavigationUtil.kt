package de.janniskilian.basket

import androidx.annotation.StringRes
import com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickOn

fun navigateToBottomNavigationDrawerDestination(@StringRes destinationLabelRes: Int) {
    clickOn(R.id.navigationButton)
    clickOn(destinationLabelRes)
}
