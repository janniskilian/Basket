package de.janniskilian.basket.feature.main

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.navigation.findNavController
import de.janniskilian.basket.R
import de.janniskilian.basket.core.ANIMATION_DURATION_M
import de.janniskilian.basket.core.BaseFragment
import de.janniskilian.basket.core.BasketApp
import de.janniskilian.basket.core.navigationcontainer.NavigationContainer
import de.janniskilian.basket.core.navigationcontainer.NavigationContainerProvider
import de.janniskilian.basket.core.util.extension.extern.setSelectedImageState
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationContainerProvider {

    private val sharedPrefs by lazy {
        (application as BasketApp).appModule.androidModule.sharedPreferences
    }

    override val navigationContainer: NavigationContainer = NavigationContainerImpl(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(appBar)
        setupNavigation()
        setClickListeners()

        if (savedInstanceState == null
            && !sharedPrefs.getBoolean(KEY_DEFAULT_DATA_IMPORTED, false)
        ) {
            OnboardingFragment().apply { show(supportFragmentManager, tag) }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuRes = currentFragment?.menuRes
        if (menuRes != null) {
            menuInflater.inflate(menuRes, menu)
        }

        return menuRes != null
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean =
        currentFragment?.onOptionsItemSelected(item) ?: false

    override fun onSupportNavigateUp(): Boolean =
        findNavController(R.id.navHost).navigateUp()

    override fun onBackPressed() {
        if (currentFragment?.onBackPressed() != true) {
            super.onBackPressed()
        }
    }

    private fun setupNavigation() {
        findNavController(R.id.navHost).addOnDestinationChangedListener { _, _, _ ->
            navigationContainer.dismissSnackbar()
        }

        navHost.childFragmentManager.registerFragmentLifecycleCallbacks(
            object : FragmentManager.FragmentLifecycleCallbacks() {
                override fun onFragmentStarted(fm: FragmentManager, fragment: Fragment) {
                    if (fragment is BaseFragment) {
                        invalidateOptionsMenu()

                        val fabTextRes = fragment.fabTextRes
                        fab.isVisible = fabTextRes != null
                        if (fabTextRes != null) {
                            setFabText(fabTextRes)
                        }
                    }
                }
            },
            false
        )

        navHost.childFragmentManager.addOnBackStackChangedListener {
            updateNavigationIcon()
        }

        navigationButton.setOnClickListener {
            if (navHost.childFragmentManager.backStackEntryCount == 0) {
                showNavigation()
            } else {
                findNavController(R.id.navHost).navigateUp()
            }
        }

        updateNavigationIcon()
    }

    private fun setClickListeners() {
        fab.setOnClickListener { currentFragment?.onFabClicked() }
    }

    private fun setFabText(buttonTextRes: Int) {
        if (fab.text.isNullOrEmpty()) {
            fab.setText(buttonTextRes)
        } else {
            fab.updateLayoutParams {
                width = fab.width
            }
            fab.setText(buttonTextRes)

            fab.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            )
            with(ValueAnimator.ofInt(fab.width, fab.measuredWidth)) {
                duration = ANIMATION_DURATION_M
                interpolator = FastOutSlowInInterpolator()
                addUpdateListener {
                    fab.updateLayoutParams {
                        width = animatedValue as Int
                    }
                }
                start()
            }
        }
    }

    private fun updateNavigationIcon() {
        navigationButton.setSelectedImageState(
            navHost.childFragmentManager.backStackEntryCount > 0
        )
    }

    private fun showNavigation() {
        findNavController(R.id.navHost).currentDestination?.id?.let { destinationId ->
            val fragment = BottomNavigationDrawerFragment.create(destinationId)
            fragment.show(supportFragmentManager, fragment.tag)
        }
    }

    val currentFragment: BaseFragment?
        get() = navHost.childFragmentManager.primaryNavigationFragment as? BaseFragment

    companion object {

        private const val KEY_DEFAULT_DATA_IMPORTED = "KEY_DEFAULT_DATA_IMPORTED"
    }
}
