package de.janniskilian.basket.core

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

open class BaseBottomSheetDialogFragment : BottomSheetDialogFragment() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setStyle(DialogFragment.STYLE_NORMAL, R.style.Widget_App_BottomSheetDialog)
	}

	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)

		(view?.parent as? View)?.setBackgroundResource(
			R.drawable.bottom_sheet_dialog_background
		)

		requireActivity().window.setSoftInputMode(
			WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING
		)
	}

	override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
		BaseBottomSheetDialog(requireContext(), theme)

	open fun onBackPressed(): Boolean =
		false

	inner class BaseBottomSheetDialog(context: Context, theme: Int) :
		BottomSheetDialog(context, theme) {

		override fun onBackPressed() {
			if (!this@BaseBottomSheetDialogFragment.onBackPressed()) {
				super.onBackPressed()
			}
		}
	}
}
