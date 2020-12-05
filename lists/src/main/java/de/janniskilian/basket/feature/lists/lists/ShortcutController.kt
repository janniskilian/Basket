package de.janniskilian.basket.feature.lists.lists

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import de.janniskilian.basket.core.type.domain.ShoppingList
import de.janniskilian.basket.core.util.function.getInt
import de.janniskilian.basket.feature.lists.R
import timber.log.Timber
import kotlin.math.min

class ShortcutController(private val context: Context) {

    fun updateShoppingListShortcuts(shoppingList: ShoppingList) {
        val maxShortcuts = ShortcutManagerCompat.getMaxShortcutCountPerActivity(context)
        val maxShoppingListShortcuts = min(
            getInt(context, R.integer.max_shopping_list_shortcuts),
            maxShortcuts
        )
        val currentShortcuts = ShortcutManagerCompat
            .getDynamicShortcuts(context)
            .filter { it.id.startsWith(SHORTCUT_ID_PREFIX) }

        val newShortcutId = SHORTCUT_ID_PREFIX + shoppingList.id.value.toString()

        if (currentShortcuts.none { it.id == newShortcutId }) {
            val n = (currentShortcuts.size + 1 - maxShoppingListShortcuts).coerceAtLeast(0)
            val droppedShortcuts = currentShortcuts
                .take(n)
                .map { it.id }
            ShortcutManagerCompat.removeDynamicShortcuts(context, droppedShortcuts)

            val newShortcut = createShoppingListShortcuts(newShortcutId, shoppingList)
            ShortcutManagerCompat.addDynamicShortcuts(context, listOf(newShortcut))
        }
    }

    private fun createShoppingListShortcuts(
        shortcutId: String,
        shoppingList: ShoppingList
    ): ShortcutInfoCompat {
        val icon = IconCompat.createWithResource(
            context,
            R.drawable.ic_shortcut_list
        )

        return ShortcutInfoCompat
            .Builder(context, shortcutId)
            .setShortLabel(shoppingList.name)
            .setLongLabel(context.getString(R.string.shortcut_list_long_label, shoppingList.name))
            .setIcon(icon)
            .setIntent(
                Intent().apply {
                    component = ComponentName(context, MAIN_ACTIVITY_CLASS)
                    action = Intent.ACTION_VIEW
                    putExtra(
                        context.getString(R.string.key_shopping_list_id),
                        shoppingList.id.value
                    )
                }
            )
            .build()
    }

    companion object {

        private const val MAIN_ACTIVITY_CLASS = "de.janniskilian.basket.feature.main.MainActivity"
        private const val SHORTCUT_ID_PREFIX = "de.janniskilian.basket.shortcut_"
    }
}
