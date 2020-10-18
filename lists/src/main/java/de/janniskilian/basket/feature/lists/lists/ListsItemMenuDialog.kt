package de.janniskilian.basket.feature.lists.lists

import android.os.Parcelable
import androidx.navigation.fragment.navArgs
import de.janniskilian.basket.core.MenuBottomSheetDialog
import de.janniskilian.basket.core.ResultCode
import de.janniskilian.basket.core.setResult
import de.janniskilian.basket.feature.lists.R
import kotlinx.android.parcel.Parcelize

class ListsItemMenuDialog : MenuBottomSheetDialog() {

    private val args: ListsItemMenuDialogArgs by navArgs()

    override val menuRes get() = R.menu.shopping_list_item

    override fun onMenuItemSelected(itemId: Int) {
        setResult(ResultCode.SUCCESS, Result(itemId, args.shoppingListId))
    }

    @Parcelize
    class Result(
        val menuItemId: Int,
        val shoppingListId: Long
    ) : Parcelable
}
