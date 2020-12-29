package de.janniskilian.basket.feature.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import de.janniskilian.basket.R
import de.janniskilian.basket.core.ui.fragments.BaseFragment
import de.janniskilian.basket.core.ui.navigation.NavigationContainer
import de.janniskilian.basket.core.ui.navigation.NavigationContainerProvider
import de.janniskilian.basket.core.util.android.getLongExtraOrNull
import de.janniskilian.basket.databinding.MainActivityBinding
import de.janniskilian.basket.feature.lists.list.ListFragmentArgs
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationContainerProvider {

    @Inject
    lateinit var preferencesDataStore: DataStore<Preferences>

    lateinit var binding: MainActivityBinding
        private set

    private val uiController = MainActivityUiController(this)

    private val setup by lazy {
        MainActivitySetup(this, uiController, preferencesDataStore)
    }

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

        setup.run(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()

        handleViewListAction(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        handleViewListAction(intent)
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

    private fun handleViewListAction(intent: Intent?) {
        if (intent?.action == Intent.ACTION_VIEW) {
            intent
                .getLongExtraOrNull(getString(R.string.key_shopping_list_id))
                ?.let {
                    findNavController().navigate(
                        R.id.listFragment,
                        ListFragmentArgs(it).toBundle()
                    )
                }

            intent.action = null
        }
    }
}
