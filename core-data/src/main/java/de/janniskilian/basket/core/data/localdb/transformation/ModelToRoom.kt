package de.janniskilian.basket.core.data.localdb.transformation

import de.janniskilian.basket.core.data.localdb.entity.RoomArticle
import de.janniskilian.basket.core.data.localdb.entity.RoomCategory
import de.janniskilian.basket.core.data.localdb.entity.RoomShoppingList
import de.janniskilian.basket.core.data.localdb.entity.RoomShoppingListItem
import de.janniskilian.basket.core.type.domain.Article
import de.janniskilian.basket.core.type.domain.Category
import de.janniskilian.basket.core.type.domain.ShoppingList
import de.janniskilian.basket.core.type.domain.ShoppingListItem
import de.janniskilian.basket.core.util.withoutSpecialChars

fun modelToRoom(category: Category): RoomCategory =
    RoomCategory(
        category.name,
        category.name.withoutSpecialChars(),
        category.id.value
    )

fun modelToRoom(article: Article): RoomArticle =
    RoomArticle(
        article.name,
        article.name.withoutSpecialChars(),
        article.category?.id?.value,
        article.id.value
    )

fun modelToRoom(shoppingList: ShoppingList): RoomShoppingList =
    RoomShoppingList(
        shoppingList.name,
        shoppingList.name.withoutSpecialChars(),
        shoppingList.color,
        shoppingList.isGroupedByCategory,
        shoppingList.id.value
    )

fun modelToRoom(shoppingListItem: ShoppingListItem): RoomShoppingListItem =
    RoomShoppingListItem(
        shoppingListItem.shoppingListId.value,
        shoppingListItem.article.id.value,
        shoppingListItem.quantity,
        shoppingListItem.comment,
        shoppingListItem.isChecked,
        shoppingListItem.id.value
    )
