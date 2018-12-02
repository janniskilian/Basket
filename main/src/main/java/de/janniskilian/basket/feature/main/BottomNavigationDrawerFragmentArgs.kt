package de.janniskilian.basket.feature.main

import android.os.Parcelable
import androidx.annotation.IdRes
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BottomNavigationDrawerFragmentArgs(
	@field:IdRes val currentNavId: Int
) : Parcelable
