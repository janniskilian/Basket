package de.janniskilian.basket.core.data.localdb.transformation

import de.janniskilian.basket.core.data.localdb.entity.RoomCategory
import de.janniskilian.basket.core.data.localdb.result.RoomArticleResult
import de.janniskilian.basket.core.data.localdb.result.RoomShoppingListItemResult
import de.janniskilian.basket.core.data.localdb.result.RoomShoppingListResult
import de.janniskilian.basket.core.type.domain.Article
import de.janniskilian.basket.core.type.domain.ArticleId
import de.janniskilian.basket.core.type.domain.Category
import de.janniskilian.basket.core.type.domain.CategoryId
import de.janniskilian.basket.core.type.domain.ShoppingList
import de.janniskilian.basket.core.type.domain.ShoppingListId
import de.janniskilian.basket.core.type.domain.ShoppingListItem
import de.janniskilian.basket.core.type.domain.ShoppingListItemId

fun roomToModel(category: RoomCategory): Category =
    Category(CategoryId(category.id), category.name)

fun roomToModel(articleResult: RoomArticleResult): Article {
	val category = articleResult.category?.let { roomToModel(it) }

	return Article(
		ArticleId(articleResult.articleId),
		articleResult.articleName,
		category
	)
}

fun roomToModel(result: List<RoomShoppingListResult>): ShoppingList =
	ShoppingList(
		ShoppingListId(result.first().shoppingListId),
		result.first().shoppingListName,
		result.first().color,
		result.mapNotNull { resultItem ->
			resultItem.shoppingListItem?.let {
				val category = if (it.categoryId == null
					|| it.categoryName == null
				) {
					null
				} else {
					Category(CategoryId(it.categoryId), it.categoryName)
				}
				ShoppingListItem(
                    ShoppingListItemId(it.id),
                    ShoppingListId(result.first().shoppingListId),
                    Article(
                        ArticleId(it.articleId),
                        it.articleName,
                        category
                    ),
                    it.quantity,
                    it.comment,
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
			CategoryId(shoppingListItem.categoryId),
			shoppingListItem.categoryName
		)
	}

	return ShoppingListItem(
        ShoppingListItemId(shoppingListItem.id),
        ShoppingListId(shoppingListItem.shoppingListId),
        Article(
            ArticleId(shoppingListItem.articleId),
            shoppingListItem.articleName,
            category
        ),
        shoppingListItem.quantity,
        shoppingListItem.comment,
        shoppingListItem.checked
    )
}
