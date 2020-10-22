package de.janniskilian.basket.feature.helpandfeedback

import android.view.LayoutInflater
import android.view.ViewGroup
import de.janniskilian.basket.R
import de.janniskilian.basket.core.BaseFragment
import de.janniskilian.basket.databinding.HelpAndFeedbackFragmentBinding

class HelpAndFeedbackFragment : BaseFragment<HelpAndFeedbackFragmentBinding>() {

    override val titleTextRes get() = R.string.help_and_feedback_title

    override fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        HelpAndFeedbackFragmentBinding.inflate(inflater, container, false)
}
