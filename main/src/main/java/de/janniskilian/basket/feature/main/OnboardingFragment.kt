package de.janniskilian.basket.feature.main

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.core.content.edit
import androidx.core.view.children
import androidx.core.view.forEach
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import de.janniskilian.basket.R
import de.janniskilian.basket.core.BaseFragment
import de.janniskilian.basket.core.KEY_DEFAULT_DATA_IMPORTED
import de.janniskilian.basket.core.data.DataClient
import de.janniskilian.basket.core.data.DefaultDataImporter
import de.janniskilian.basket.core.data.DefaultDataLoader
import de.janniskilian.basket.core.util.extension.extern.getThemeColor
import de.janniskilian.basket.core.util.viewmodel.DefaultMutableLiveData
import kotlinx.android.synthetic.main.fragment_onboarding.*
import kotlinx.android.synthetic.main.language_item.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class OnboardingFragment : BaseFragment() {

    @Inject
    lateinit var sharedPrefs: SharedPreferences

    @Inject
    lateinit var dataClient: DataClient

    private val locales by lazy {
        resources
            .getStringArray(R.array.onboarding_presets_languages)
            .map { Locale.forLanguageTag(it) }
    }

    private var selectedLocale: Locale
        get() {
            val position = radioGroup.children.indexOfFirst { it.radioButton.isChecked }
            return locales.getOrNull(position) ?: Locale.getDefault()
        }
        set(value) {
            val id = value.hashCode()
            radioGroup.forEach {
                it.radioButton.isChecked = it.id == id
            }
        }

    override val layoutRes get() = R.layout.fragment_onboarding

    override val isShowAppBar get() = false

    override val appBarColor by lazy {
        DefaultMutableLiveData(requireContext().getThemeColor(android.R.attr.colorBackground))
    }

    override val animateAppBarColor get() = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setClickListeners()

        locales.forEach { locale ->
            val item = layoutInflater
                .inflate(R.layout.language_item, radioGroup, false)
                .apply {
                    id = locale.hashCode()
                    name.text = locale.displayLanguage

                    setOnClickListener { selectedLocale = locale }
                }

            radioGroup.addView(item)
        }

        selectedLocale = locales.first()
    }

    private fun setClickListeners() {
        button.setOnClickListener {
            GlobalScope.launch {
                DefaultDataImporter(
                    dataClient,
                    DefaultDataLoader(requireContext(), selectedLocale)
                ).run()

                launch(Dispatchers.Main) {
                    sharedPrefs.edit {
                        putBoolean(KEY_DEFAULT_DATA_IMPORTED, true)
                    }

                    findNavController().popBackStack()
                }
            }
        }
    }
}
