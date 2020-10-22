package de.janniskilian.basket.feature.main

import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import de.janniskilian.basket.R
import de.janniskilian.basket.core.BaseFragment
import de.janniskilian.basket.core.navigationcontainer.NavigationContainer
import de.janniskilian.basket.core.navigationcontainer.NavigationContainerProvider
import de.janniskilian.basket.databinding.MainActivityBinding
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationContainerProvider {

    @Inject
    lateinit var sharedPrefs: SharedPreferences

    private val uiController = MainActivityUiController(this)
    private val setup = MainActivitySetup(this, uiController)
    lateinit var binding: MainActivityBinding
        private set

    val navHostFragment
        get() = supportFragmentManager
            .findFragmentByTag(getString(R.string.tag_nav_host_fragment))!!

    val currentFragment: BaseFragment<*>?
        get() = navHostFragment.childFragmentManager.primaryNavigationFragment as? BaseFragment<*>

    override val navigationContainer: NavigationContainer = NavigationContainerImpl(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setup.run()

        if (savedInstanceState == null
            && !sharedPrefs.getBoolean(KEY_DEFAULT_DATA_IMPORTED, false)
        ) {
            findNavController().navigate(R.id.onboardingFragment)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuRes = currentFragment?.menuRes
        if (menuRes != null) {
            menuInflater.inflate(menuRes, menu)
        }

        return menuRes != null
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        currentFragment?.onOptionsItemSelected(item) ?: false

    override fun onSupportNavigateUp(): Boolean =
        findNavController().navigateUp()

    override fun onBackPressed() {
        if (currentFragment?.onNavigateUpAction() != true
            && currentFragment?.onBackPressed() != true
        ) {
            super.onBackPressed()
        }
    }

    fun findNavController() = navHostFragment.findNavController()

    companion object {

        private const val KEY_DEFAULT_DATA_IMPORTED = "KEY_DEFAULT_DATA_IMPORTED"
    }
}
