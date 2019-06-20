package de.janniskilian.basket.core.testing

import de.janniskilian.basket.core.type.domain.*
import kotlin.math.pow
import kotlin.random.Random

private fun Random.nextString(length: Int = 8): String =
	(nextFloat() * 10f.pow(length)).toInt().toString()

fun createTestCategory(): Category =
	Category(
        CategoryId(Random.nextLong()),
		Random.nextString()
	)

fun createTestArticle(category: Category? = createTestCategory()): Article =
	Article(
        ArticleId(Random.nextLong()),
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
        ShoppingListItemId(Random.nextLong()),
		shoppingList.id,
		article,
		quantity.toString(),
		checked
	)

fun createTestShoppingList(items: List<ShoppingListItem> = emptyList()): ShoppingList =
	ShoppingList(
        ShoppingListId(Random.nextLong()),
		Random.nextString(),
		Random.nextInt(),
		items
	)

fun createTestShoppingList(itemCount: Int): ShoppingList =
	createTestShoppingList().apply {
		copy(
			items = List(itemCount) {
				createTestShoppingListItem(this)
			}
		)
	}
