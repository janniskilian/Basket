package de.janniskilian.basket.feature.lists.addlistitem

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AddListItemFragmentArgs(
	val shoppingListId: Long
) : Parcelable
