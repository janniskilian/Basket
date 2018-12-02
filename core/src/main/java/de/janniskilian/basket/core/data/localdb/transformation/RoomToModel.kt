package de.janniskilian.basket.core.data.localdb.transformation

import de.janniskilian.basket.core.data.localdb.entity.RoomCategory
import de.janniskilian.basket.core.data.localdb.result.RoomArticleResult
import de.janniskilian.basket.core.data.localdb.result.RoomShoppingListItemResult
import de.janniskilian.basket.core.data.localdb.result.RoomShoppingListResult
import de.janniskilian.basket.core.type.domain.Article
import de.janniskilian.basket.core.type.domain.Category
import de.janniskilian.basket.core.type.domain.ShoppingList
import de.janniskilian.basket.core.type.domain.ShoppingListItem

fun roomToModel(category: RoomCategory): Category =
	Category(category.id, category.name)

fun roomToModel(articleResult: RoomArticleResult): Article {
	val category = articleResult.category?.let { roomToModel(it) }

	return Article(
		articleResult.articleId,
		articleResult.articleName,
		category
	)
}

fun roomToModel(result: List<RoomShoppingListResult>): ShoppingList =
	ShoppingList(
		result.first().shoppingListId,
		result.first().shoppingListName,
		result.first().color,
		result.mapNotNull { resultItem ->
			resultItem.shoppingListItem?.let {
				val category = if (it.categoryId == null
					|| it.categoryName == null
				) {
					null
				} else {
					Category(it.categoryId, it.categoryName)
				}
				ShoppingListItem(
					it.id,
					result.first().shoppingListId,
					Article(
						it.articleId,
						it.articleName,
						category
					),
					it.quantity,
					it.checked
				)
			}
		}
	)

fun roomToModel(shoppingListItem: RoomShoppingListItemResult): ShoppingListItem {
	val category = if (shoppingListItem.categoryId == null
		|| shoppingListItem.categoryName == null
	) {
		null
	} else {
		Category(
			shoppingListItem.categoryId,
			shoppingListItem.categoryName
		)
	}

	return ShoppingListItem(
		shoppingListItem.id,
		shoppingListItem.shoppingListId,
		Article(
			shoppingListItem.articleId,
			shoppingListItem.articleName,
			category
		),
		shoppingListItem.quantity,
		shoppingListItem.checked
	)
}
