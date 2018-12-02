package de.janniskilian.basket.core.type.datapassing

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CategoryFragmentArgs(
	val categoryId: Long?
) : Parcelable
