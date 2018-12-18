package de.janniskilian.basket.feature.groups

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.janniskilian.basket.core.BaseFragment
import de.janniskilian.feature.groups.R

class GroupsFragment : BaseFragment() {

    override val fabTextRes: Int?
        get() = R.string.fab_create_group

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        inflater.inflate(
            R.layout.fragment_groups,
            container,
            false
        )
}
