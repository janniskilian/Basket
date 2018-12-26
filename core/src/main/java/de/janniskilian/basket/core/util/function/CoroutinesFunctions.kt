package de.janniskilian.basket.core.util.function

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun <T> withIOContext(block: suspend CoroutineScope.() -> T): T =
    withContext(Dispatchers.IO, block)
