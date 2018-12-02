package de.janniskilian.basket.feature.lists.addlistitem

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import de.janniskilian.basket.core.REQ_SPEECH_INPUT
import de.janniskilian.basket.core.appModule
import de.janniskilian.basket.core.getArgs
import de.janniskilian.basket.core.putArgs
import de.janniskilian.basket.core.util.function.getSpeechInputResult
import de.janniskilian.basket.feature.lists.R
import kotlinx.android.synthetic.main.fragment_lists.*

class AddListItemFragment : DialogFragment() {

	private val module by lazy {
		AddListItemModule(appModule, this, getArgs())
	}

	private val setup get() = module.addListItemSetup

	private val viewModel get() = module.addListItemViewModel

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setStyle(DialogFragment.STYLE_NORMAL, R.style.Widget_App_FullHeightDialog)
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View =
		inflater.inflate(
			R.layout.fragment_add_list_item,
			container,
			false
		)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		setup.run()
	}

	override fun onDestroyView() {
		recyclerView.adapter = null
		super.onDestroyView()
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		if (requestCode == REQ_SPEECH_INPUT
			&& resultCode == Activity.RESULT_OK
		) {
			getSpeechInputResult(data)?.let {
				viewModel.setInput(it)
			}
		}
	}

	companion object {

		fun create(shoppingListId: Long): AddListItemFragment =
			AddListItemFragment().putArgs(AddListItemFragmentArgs(shoppingListId))
	}
}

