package de.janniskilian.basket.core.data

import androidx.lifecycle.LiveData
import de.janniskilian.basket.core.type.domain.Article
import de.janniskilian.basket.core.type.domain.ArticleId
import de.janniskilian.basket.core.type.domain.ShoppingListId
import de.janniskilian.basket.core.type.domain.ShoppingListItem
import de.janniskilian.basket.core.type.domain.ShoppingListItemId

interface ShoppingListItemDataClient {

    suspend fun create(
        shoppingListId: ShoppingListId,
        article: Article,
        quantity: String = "",
        comment: String = "",
        isChecked: Boolean = false
    )

    suspend fun create(shoppingListItems: List<ShoppingListItem>)

    suspend fun get(shoppingListItemId: ShoppingListItemId): ShoppingListItem?

    fun getLiveData(shoppingListItemId: ShoppingListItemId): LiveData<ShoppingListItem>

    suspend fun update(shoppingListItem: ShoppingListItem)

    suspend fun setAllCheckedForShoppingList(shoppingListId: ShoppingListId, isChecked: Boolean)

    suspend fun delete(shoppingListId: ShoppingListId, articleId: ArticleId)

    suspend fun deleteAllForShoppingList(shoppingListId: ShoppingListId)

    suspend fun deleteAllCheckedForShoppingList(shoppingListId: ShoppingListId)
}
