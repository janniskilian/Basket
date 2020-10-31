package de.janniskilian.basket.feature.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import de.janniskilian.basket.R
import de.janniskilian.basket.core.BaseFragment
import de.janniskilian.basket.core.navigationcontainer.NavigationContainer
import de.janniskilian.basket.core.navigationcontainer.NavigationContainerProvider
import de.janniskilian.basket.databinding.MainActivityBinding
import de.janniskilian.basket.feature.lists.list.ListFragmentArgs
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationContainerProvider {

    @Inject
    lateinit var dataStore: DataStore<Preferences>

    lateinit var binding: MainActivityBinding
        private set

    private val uiController = MainActivityUiController(this)

    private val setup by lazy {
        MainActivitySetup(this, uiController, dataStore)
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

        if (intent?.action == getString(R.string.view_list_action)) {
            val shoppingListId = intent.getLongExtra(getString(R.string.key_shopping_list_id), -1)
            if (shoppingListId != -1L) {
                findNavController().navigate(
                    R.id.listFragment,
                    ListFragmentArgs(shoppingListId).toBundle()
                )
            }

            intent?.action = null
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
}
