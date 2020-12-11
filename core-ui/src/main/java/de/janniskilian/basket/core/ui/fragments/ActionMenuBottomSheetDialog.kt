package de.janniskilian.basket.core.ui.fragments

import android.os.Parcelable
import androidx.navigation.fragment.navArgs
import de.janniskilian.basket.core.ui.navigation.ResultCode
import de.janniskilian.basket.core.ui.navigation.setResult
import kotlinx.parcelize.Parcelize

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
