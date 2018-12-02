package de.janniskilian.basket.feature.categories.category

import de.janniskilian.basket.core.data.DataClient
import de.janniskilian.basket.core.type.datapassing.CategoryFragmentArgs
import de.janniskilian.basket.core.util.extension.extern.observe
import de.janniskilian.basket.core.util.extension.extern.onDone
import de.janniskilian.basket.feature.categories.R
import kotlinx.android.synthetic.main.fragment_category.*

class CategoryFragmentSetup(
	private val fragment: CategoryFragment,
	private val args: CategoryFragmentArgs,
	private val eventHandler: CategoryEventHandler,
	private val dataClient: DataClient
) {

	fun run() {
		setButtonText()
		setCategoryData()
		setClickListeners()
	}

	private fun setButtonText() {
		val buttonTextRes = if (args.categoryId == null) {
			R.string.create_category_button
		} else {
			R.string.save_category_button
		}
		fragment.submitButton.setText(buttonTextRes)
	}

	private fun setCategoryData() {
		args.categoryId?.let { categoryId ->
			dataClient
				.category
				.get(categoryId)
				.observe(fragment) {
					fragment.nameEditText.setText(it.name)
				}
		}
	}

	private fun setClickListeners() {
		fragment.submitButton.setOnClickListener { submit() }
		fragment.nameEditText.onDone { submit() }
	}

	private fun submit() {
		eventHandler.createButtonClicked(
			fragment.nameEditText.text.toString()
		)
	}
}
