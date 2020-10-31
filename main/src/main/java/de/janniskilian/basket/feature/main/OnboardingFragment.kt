package de.janniskilian.basket.feature.main

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.core.view.children
import androidx.core.view.forEach
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.edit
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
import de.janniskilian.basket.databinding.OnboardingFragmentBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class OnboardingFragment : BaseFragment<OnboardingFragmentBinding>() {

    @Inject
    lateinit var sharedPrefs: SharedPreferences

    @Inject
    lateinit var dataStore: DataStore<Preferences>

    @Inject
    lateinit var dataClient: DataClient

    private val locales by lazy {
        resources
            .getStringArray(R.array.onboarding_presets_languages)
            .map { Locale.forLanguageTag(it) }
    }

    private var selectedLocale: Locale
        get() {
            val position = binding.radioGroup.children.indexOfFirst {
                it.findViewById<RadioButton>(R.id.radioButton).isChecked
            }
            return locales.getOrNull(position) ?: Locale.getDefault()
        }
        set(value) {
            val id = value.hashCode()
            binding.radioGroup.forEach {
                it.findViewById<RadioButton>(R.id.radioButton).isChecked = it.id == id
            }
        }

    override val isShowAppBar get() = false

    override val appBarColor by lazy {
        DefaultMutableLiveData(requireContext().getThemeColor(android.R.attr.colorBackground))
    }

    override val animateAppBarColor get() = false

    override fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        OnboardingFragmentBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setClickListeners()

        locales.forEach { locale ->
            val item = layoutInflater
                .inflate(R.layout.language_item, binding.radioGroup, false)
                .apply {
                    id = locale.hashCode()
                    findViewById<TextView>(R.id.name).text = locale.displayLanguage

                    setOnClickListener { selectedLocale = locale }
                }

            binding.radioGroup.addView(item)
        }

        selectedLocale = locales.first()
    }

    private fun setClickListeners() {
        binding.button.setOnClickListener {
            binding.progressIndicator.show()

            GlobalScope.launch {
                DefaultDataImporter(
                    dataClient,
                    DefaultDataLoader(requireContext(), selectedLocale)
                ).run()

                launch(Dispatchers.Main) {
                    dataStore.edit {
                        it[KEY_DEFAULT_DATA_IMPORTED] = true
                    }

                    findNavController().popBackStack()
                }
            }
        }
    }
}
