package de.janniskilian.basket.core.type.domain

data class Article(
	val id: Long,
	val name: String,
	val category: Category?
)
