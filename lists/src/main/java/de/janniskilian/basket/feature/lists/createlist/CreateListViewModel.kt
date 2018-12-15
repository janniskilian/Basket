package de.janniskilian.basket.feature.lists.createlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.janniskilian.basket.core.data.DataClient
import de.janniskilian.basket.core.util.function.createMutableLiveData
import de.janniskilian.basket.core.util.viewmodel.DefaultMutableLiveData
import de.janniskilian.basket.core.util.viewmodel.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CreateListViewModel(
	private val shoppingListId: Long?,
	val colors: List<Int>,
	private val useCases: CreateListFragmentUseCases,
	dataClient: DataClient
) : ViewModel() {

	private val _name = MutableLiveData<String>()

	private val _selectedColor =
		DefaultMutableLiveData(colors.first())

	private val _error = createMutableLiveData(false)

	private val _startList = SingleLiveEvent<Long>()

	private val _dismiss = SingleLiveEvent<Unit>()

	init {
		if (shoppingListId != null) {
			GlobalScope.launch(Dispatchers.Main) {
				dataClient.shoppingList.get(shoppingListId)?.let {
					setName(it.name)
					setSelectedColor(it.color)
				}
			}
		}
	}

	val name: LiveData<String>
		get() = _name

	val selectedColor: LiveData<Int>
		get() = _selectedColor

	val error: LiveData<Boolean>
		get() = _error

	val startList: LiveData<Long>
		get() = _startList

	val dismiss: LiveData<Unit>
		get() = _dismiss

	fun setName(name: String) {
		_name.value = name
		_error.value = false
	}

	fun setSelectedColor(color: Int) {
		_selectedColor.value = color
	}

	fun submitButtonClicked() {
		val name = name.value
		when {
			name.isNullOrBlank() -> _error.value = true

			shoppingListId == null ->
				GlobalScope.launch(Dispatchers.Main) {
					val id = useCases.createList(name, _selectedColor.value)
					_startList.setValue(id)
				}

			else -> {
				useCases.updateList(shoppingListId, name, _selectedColor.value)
				_dismiss.setValue(Unit)
			}
		}
	}
}