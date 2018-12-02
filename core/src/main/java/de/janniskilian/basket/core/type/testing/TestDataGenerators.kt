package de.janniskilian.basket.core.type.testing

import de.janniskilian.basket.core.type.domain.Article
import de.janniskilian.basket.core.type.domain.Category
import de.janniskilian.basket.core.type.domain.ShoppingList
import de.janniskilian.basket.core.type.domain.ShoppingListItem
import kotlin.math.pow
import kotlin.random.Random

private fun Random.nextString(length: Int = 8): String =
	(nextFloat() * 10f.pow(length)).toInt().toString()

fun createTestCategory(): Category =
	Category(
		Random.nextLong(),
		Random.nextString()
	)

fun createTestArticle(category: Category? = createTestCategory()): Article =
	Article(
		Random.nextLong(),
		Random.nextString(),
		category
	)

fun createTestShoppingListItem(
	shoppingList: ShoppingList,
	article: Article = createTestArticle(),
	quantity: Int = Random.nextInt(),
	checked: Boolean = Random.nextBoolean()
): ShoppingListItem =
	ShoppingListItem(
		Random.nextLong(),
		shoppingList.id,
		article,
		quantity.toString(),
		checked
	)

fun createTestShoppingList(items: List<ShoppingListItem> = emptyList()): ShoppingList =
	ShoppingList(
		Random.nextLong(),
		Random.nextString(),
		Random.nextInt(),
		items
	)

fun createTestShoppingList(itemCount: Int): ShoppingList =
	createTestShoppingList().apply {
		copy(
			items = (0 until itemCount).map {
				createTestShoppingListItem(this)
			}
		)
	}
