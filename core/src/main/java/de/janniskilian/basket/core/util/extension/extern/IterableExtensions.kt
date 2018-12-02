package de.janniskilian.basket.core.util.extension.extern

inline fun <T, R, C : MutableCollection<in R>> Iterable<T>.flatMapIndexedTo(
	destination: C,
	transform: (index: Int, element: T) -> Iterable<R>
): C {
	for ((index, element) in this.withIndex()) {
		val list = transform(index, element)
		destination.addAll(list)
	}
	return destination
}

inline fun <T, R> Iterable<T>.flatMapIndexed(transform: (Int, T) -> Iterable<R>): List<R> =
	flatMapIndexedTo(ArrayList(), transform)
