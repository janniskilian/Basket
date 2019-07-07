package de.janniskilian.basket.core.listitem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import de.janniskilian.basket.core.R
import de.janniskilian.basket.core.appModule
import kotlinx.android.synthetic.main.fragment_list_item.*

class ListItemFragment : Fragment() {

    private val args by lazy { ListItemFragmentArgs.fromBundle(requireArguments()) }

    private val module by lazy {
        ListItemModule(appModule, this, args)
    }

    private val viewModel get() = module.listItemViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_list_item, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.listItem.observe(this) {
            articleNameEditText.setText(it.article.name)
        }
    }
}
