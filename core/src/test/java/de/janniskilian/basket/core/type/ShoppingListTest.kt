package de.janniskilian.basket.core.type

import de.janniskilian.basket.core.test.createTestShoppingList
import de.janniskilian.basket.core.test.createTestShoppingListItem
import org.junit.Test
import kotlin.test.assertEquals

class ShoppingListTest {

    @Test
    fun testCheckedItemCount() {
        val list = createTestShoppingList().let {
            val item1 =
                createTestShoppingListItem(
                    it,
                    isChecked = false
                )
            val item2 =
                createTestShoppingListItem(
                    it,
                    isChecked = false
                )
            val item3 =
                createTestShoppingListItem(
                    it,
                    isChecked = false
                )
            val item4 =
                createTestShoppingListItem(
                    it,
                    isChecked = true
                )
            val item5 =
                createTestShoppingListItem(
                    it,
                    isChecked = true
                )

            it.copy(
                items = listOf(
                    item1,
                    item2,
                    item3,
                    item4,
                    item5
                )
            )
        }

        assertEquals(5, list.items.size)
        assertEquals(2, list.checkedItemCount)
    }
}
