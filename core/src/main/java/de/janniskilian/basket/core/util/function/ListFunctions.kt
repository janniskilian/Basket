package de.janniskilian.basket.core.util.function

fun <E> List<E>.addToFront(element: E): List<E> =
	listOf(element) + this
