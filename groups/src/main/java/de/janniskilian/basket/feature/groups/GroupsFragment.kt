package de.janniskilian.basket.feature.groups

import android.view.LayoutInflater
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import de.janniskilian.basket.core.ui.fragments.BaseFragment
import de.janniskilian.feature.groups.R
import de.janniskilian.feature.groups.databinding.GroupsFragmentBinding

@AndroidEntryPoint
class GroupsFragment : BaseFragment<GroupsFragmentBinding>() {

    override val titleTextRes get() = R.string.groups_title

    override val fabTextRes get() = R.string.fab_create_group

    override fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        GroupsFragmentBinding.inflate(inflater, container, false)
}
