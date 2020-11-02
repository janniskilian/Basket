package de.janniskilian.basket.feature.lists.lists

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.pm.ShortcutManagerCompat
import de.janniskilian.basket.core.type.domain.ShoppingList
import de.janniskilian.basket.core.util.function.getInt
import de.janniskilian.basket.feature.lists.R

class ShortcutController(private val context: Context) {

    private val maxShoppingListShortcuts = getInt(context, R.integer.max_shopping_list_shortcuts)

    @RequiresApi(Build.VERSION_CODES.N_MR1)
    fun createShoppingListShortcut(shoppingList: ShoppingList) {
        val shortcuts = ShortcutManagerCompat.getDynamicShortcuts(context)
        val newShortcutId = shoppingList.id.value.toString()
        val isListInShortcuts = shortcuts.any { it.id == newShortcutId }

        /*val rem = if (shortcuts.size >= maxShoppingListShortcuts) {

        } else {

        }*/

        //ShortcutManagerCompat.removeDynamicShortcuts(context, list)

        val shortcutManager = context.getSystemService(ShortcutManager::class.java)
        val icon = Icon.createWithResource(
            context,
            R.drawable.ic_shortcut_list
        )

        val info = ShortcutInfo
            .Builder(context, newShortcutId)
            .setShortLabel(shoppingList.name)
            .setLongLabel(context.getString(R.string.shortcut_list_long_label, shoppingList.name))
            .setIcon(icon)
            .setIntent(
                Intent().apply {
                    component = ComponentName(
                        context,
                        "de.janniskilian.basket.feature.main.MainActivity"
                    )
                    action = context.getString(R.string.view_list_action)
                    putExtra(
                        context.getString(R.string.key_shopping_list_id),
                        shoppingList.id.value
                    )
                }
            )
            .build()

        shortcutManager.dynamicShortcuts = listOf(info)
    }
}
