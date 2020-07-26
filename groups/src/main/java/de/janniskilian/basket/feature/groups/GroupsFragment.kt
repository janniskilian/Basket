package de.janniskilian.basket.feature.groups

import dagger.hilt.android.AndroidEntryPoint
import de.janniskilian.basket.core.BaseFragment
import de.janniskilian.feature.groups.R

@AndroidEntryPoint
class GroupsFragment : BaseFragment() {

    override val layoutRes get() = R.layout.fragment_groups

    override val titleTextRes get() = R.string.groups_title

    override val fabTextRes get() = R.string.fab_create_group
}
