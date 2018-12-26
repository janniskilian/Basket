package de.janniskilian.basket.feature.lists.list

import android.content.SharedPreferences
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.janniskilian.basket.core.util.extension.extern.keepScreenOn
import de.janniskilian.basket.core.util.recyclerview.EndSpacingDecoration
import de.janniskilian.basket.feature.lists.R
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragmentSetup(
    private val fragment: ListFragment,
    private val viewModel: ListViewModel,
    private val viewModelObserver: ListViewModelObserver,
    private val sharedPrefs: SharedPreferences
) {

    fun run() {
        setupWindow()
        setupRecyclerView()
        viewModelObserver.observe()
    }

    private fun setupWindow() {
        val preventSleep = sharedPrefs.getBoolean(
            fragment.getString(R.string.pref_key_prevent_sleep),
            fragment.resources.getBoolean(R.bool.pref_def_prevent_sleep)
        )
        fragment
            .requireActivity()
            .window
            .keepScreenOn(preventSleep)
    }

    private fun setupRecyclerView() {
        val displayCompact = sharedPrefs.getBoolean(
            fragment.getString(R.string.pref_key_compact_lists),
            fragment.resources.getBoolean(R.bool.pref_def_compact_lists)
        )

        with(fragment.recyclerView) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(
                fragment.requireContext(),
                RecyclerView.VERTICAL,
                false
            )
            adapter = ShoppingListAdapter(fragment.requireContext(), displayCompact)
            (itemAnimator as DefaultItemAnimator).supportsChangeAnimations = false
            addItemDecoration(
                EndSpacingDecoration(
                    fragment.resources.getDimensionPixelSize(R.dimen.five),
                    RecyclerView.VERTICAL,
                    EndSpacingDecoration.Position.END
                )
            )
        }

        fragment.shoppingListAdapter?.listItemClickListener = viewModel::listItemClicked
    }
}
