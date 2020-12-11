package de.janniskilian.basket.core.ui.navigation

import android.os.Bundle
import android.os.Parcelable
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavBackStackEntry
import androidx.navigation.fragment.findNavController
import de.janniskilian.basket.core.util.android.getIntOrNull

val Fragment.requestCode: Int?
    get() = arguments?.getIntOrNull(KEY_REQUEST_CODE)

fun Fragment.setResult(resultCode: ResultCode, data: Parcelable? = null) {
    findNavController()
        .previousBackStackEntry
        ?.savedStateHandle
        ?.set(
            KEY_RESULT,
            bundleOf(
                KEY_REQUEST_CODE to requestCode,
                KEY_RESULT_CODE to resultCode,
                KEY_RESULT_DATA to data
            )
        )
}

fun Fragment.clearResult() {
    findNavController()
        .currentBackStackEntry
        ?.savedStateHandle
        ?.remove<Bundle>(KEY_RESULT)
}

inline fun NavBackStackEntry.getNavigationResult(
    block: (requestCode: Int, resultCode: ResultCode, data: Parcelable?) -> Unit
) {
    savedStateHandle
        .get<Bundle>(KEY_RESULT)
        ?.run {
            val requestCode = getInt(KEY_REQUEST_CODE, -1)
            val resultCode = getSerializable(KEY_RESULT_CODE) as? ResultCode
            val data = getParcelable<Parcelable>(KEY_RESULT_DATA)

            if (requestCode != -1 && resultCode != null) {
                block(requestCode, resultCode, data)
            }
        }
}

enum class ResultCode {

    SUCCESS,
    CANCEL
}

const val KEY_RESULT = "KEY_RESULT"
const val KEY_REQUEST_CODE = "KEY_REQUEST_CODE"
const val KEY_RESULT_CODE = "KEY_RESULT_CODE"
const val KEY_RESULT_DATA = "KEY_RESULT_DATA"
