package de.janniskilian.basket.core.test

import de.janniskilian.basket.core.type.domain.Article
import de.janniskilian.basket.core.type.domain.ArticleId
import de.janniskilian.basket.core.type.domain.Category
import de.janniskilian.basket.core.type.domain.CategoryId
import de.janniskilian.basket.core.type.domain.ShoppingList
import de.janniskilian.basket.core.type.domain.ShoppingListId
import de.janniskilian.basket.core.type.domain.ShoppingListItem
import de.janniskilian.basket.core.type.domain.ShoppingListItemId
import kotlin.math.pow
import kotlin.random.Random

private const val RANDOM_STRING_DEFAULT_LENGTH = 8
private const val RANDOM_STRING_EXPANSION_BASE = 10f

private fun Random.nextString(length: Int = RANDOM_STRING_DEFAULT_LENGTH): String =
    (nextFloat() * RANDOM_STRING_EXPANSION_BASE.pow(length))
        .toInt()
        .toString()

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
    comment: String = "",
    isChecked: Boolean = Random.nextBoolean()
): ShoppingListItem =
    ShoppingListItem(
        ShoppingListItemId(Random.nextLong()),
        shoppingList.id,
        article,
        quantity.toString(),
        comment,
        isChecked
    )

fun createTestShoppingList(items: List<ShoppingListItem> = emptyList()): ShoppingList =
    ShoppingList(
        ShoppingListId(Random.nextLong()),
        Random.nextString(),
        Random.nextInt(),
        items
    )

