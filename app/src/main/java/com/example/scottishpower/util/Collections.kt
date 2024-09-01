package com.example.scottishpower.util

inline fun <T, R : Comparable<R>> Iterable<T>.sortedBy(
    ascending: Boolean,
    crossinline selector: (T) -> R?
): List<T> {
    return sortedWith(if (ascending) compareBy(selector) else compareByDescending(selector))
}

fun <T> List<T>.firstOrThrow(exception: Exception = NoSuchElementException("List is empty.")): T {
    if (isEmpty()) throw exception else return first()
}