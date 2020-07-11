package de.janniskilian.basket.feature.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import de.janniskilian.basket.R
import de.janniskilian.basket.core.BaseFragment
import de.janniskilian.basket.core.BasketApp
import de.janniskilian.basket.core.navigationcontainer.NavigationContainer
import de.janniskilian.basket.core.navigationcontainer.NavigationContainerProvider

class MainActivity : AppCompatActivity(), NavigationContainerProvider {

    private val sharedPrefs by lazy {
        (application as BasketApp).appModule.androidModule.sharedPreferences
    }

    private val uiController = MainActivityUiController(this)
    private val setup = MainActivitySetup(this, uiController)

    val navHostFragment
        get() = supportFragmentManager
            .findFragmentByTag(getString(R.string.tag_nav_host_fragment))!!

    val currentFragment: BaseFragment?
        get() = navHostFragment.childFragmentManager.primaryNavigationFragment as? BaseFragment

    override val navigationContainer: NavigationContainer = NavigationContainerImpl(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
