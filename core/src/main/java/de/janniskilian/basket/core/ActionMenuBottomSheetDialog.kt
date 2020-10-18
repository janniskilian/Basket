package de.janniskilian.basket.core

import android.os.Parcelable
import androidx.navigation.fragment.navArgs
import kotlinx.android.parcel.Parcelize

class ActionMenuBottomSheetDialog : MenuBottomSheetDialog() {

    private val args: ActionMenuBottomSheetDialogArgs by navArgs()

    override val menuRes get() = args.menuRes

    override fun onMenuItemSelected(itemId: Int) {
        setResult(ResultCode.SUCCESS, Result(itemId))
    }

    @Parcelize
    class Result(
        val menuItemId: Int
    ) : Parcelable
}
