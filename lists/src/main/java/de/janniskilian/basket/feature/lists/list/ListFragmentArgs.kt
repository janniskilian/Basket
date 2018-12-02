package de.janniskilian.basket.feature.lists.list

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ListFragmentArgs(
	val shoppingListId: Long
) : Parcelable
