package de.janniskilian.basket.core.type.domain

data class Category(
    val id: CategoryId,
    override val name: String
) : NamedItem
