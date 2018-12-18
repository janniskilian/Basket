package de.janniskilian.basket.core.type

import de.janniskilian.basket.core.testing.createTestShoppingList
import de.janniskilian.basket.core.testing.createTestShoppingListItem
import org.junit.Test
import kotlin.test.assertEquals

class ShoppingListTest {

    @Test
    fun `test checkedItemCount`() {
        val list = createTestShoppingList().let {
            val item1 =
                createTestShoppingListItem(it, checked = false)
            val item2 =
                createTestShoppingListItem(it, checked = false)
            val item3 =
                createTestShoppingListItem(it, checked = false)
            val item4 =
                createTestShoppingListItem(it, checked = true)
            val item5 =
                createTestShoppingListItem(it, checked = true)

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
