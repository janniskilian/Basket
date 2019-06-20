package de.janniskilian.basket.core.data.localdb.transformation

import de.janniskilian.basket.core.data.localdb.entity.RoomArticle
import de.janniskilian.basket.core.data.localdb.entity.RoomCategory
import de.janniskilian.basket.core.data.localdb.entity.RoomShoppingList
import de.janniskilian.basket.core.data.localdb.entity.RoomShoppingListItem
import de.janniskilian.basket.core.type.domain.Article
import de.janniskilian.basket.core.type.domain.Category
import de.janniskilian.basket.core.type.domain.ShoppingList
import de.janniskilian.basket.core.type.domain.ShoppingListItem

fun modelToRoom(category: Category): RoomCategory =
	RoomCategory(category.name, category.id.value)

fun modelToRoom(article: Article): RoomArticle =
	RoomArticle(article.name, article.category?.id?.value, article.id.value)

fun modelToRoom(shoppingList: ShoppingList): RoomShoppingList =
	RoomShoppingList(shoppingList.name, shoppingList.color, shoppingList.id.value)

fun modelToRoom(shoppingListItem: ShoppingListItem): RoomShoppingListItem =
	RoomShoppingListItem(
		shoppingListItem.shoppingListId.value,
		shoppingListItem.article.id.value,
		shoppingListItem.quantity,
		shoppingListItem.checked,
		shoppingListItem.id.value
	)
