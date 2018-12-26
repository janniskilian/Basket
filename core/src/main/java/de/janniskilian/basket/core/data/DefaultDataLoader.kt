package de.janniskilian.basket.core.data

import android.content.Context
import android.content.res.Configuration
import android.util.JsonReader
import android.util.JsonToken
import androidx.annotation.RawRes
import de.janniskilian.basket.core.R
import de.janniskilian.basket.core.data.localdb.entity.RoomArticle
import de.janniskilian.basket.core.data.localdb.entity.RoomCategory
import de.janniskilian.basket.core.util.function.withIOContext
import java.util.*

class DefaultDataLoader(context: Context, locale: Locale = Locale.getDefault()) {

    private val localizedContext by lazy {
        val conf = Configuration(context.resources.configuration).also {
            it.setLocale(locale)
        }
        context.createConfigurationContext(conf)
    }

    suspend fun loadCategories(): List<RoomCategory> = withIOContext {
        readArray(R.raw.categories) { reader, _ ->
            var id: Long? = null
            var name: String? = null

            while (reader.hasNext()) {
                val fieldName = reader.nextName()
                when (fieldName) {
                    ID -> id = reader.nextLong()

                    NAME -> name = reader.nextString()

                    else -> reader.skipValue()
                }
            }

            if (id != null && name != null) {
                RoomCategory(name, id)
            } else {
                null
            }
        }
    }

    suspend fun loadArticles(): List<RoomArticle> = withIOContext {
        readArray(R.raw.articles) { reader, index ->
            var name: String? = null
            var categoryId: Long? = null

            while (reader.hasNext()) {
                val fieldName = reader.nextName()
                when (fieldName) {
                    NAME -> name = reader.nextString()

                    CATEGORY_ID -> {
                        if (reader.peek() == JsonToken.NUMBER) {
                            categoryId = reader.nextLong()
                        } else {
                            reader.skipValue()
                        }
                    }

                    else -> reader.skipValue()
                }
            }

            if (name != null) {
                RoomArticle(name, categoryId, index + 1L)
            } else {
                null
            }
        }
    }

    private inline fun <T> readArray(
        @RawRes resId: Int,
        parse: (reader: JsonReader, index: Int) -> T?
    ): List<T> {
        val list = mutableListOf<T>()
        var index = 0

        JsonReader(localizedContext.resources.openRawResource(resId).reader()).use { reader ->
            reader.beginArray()

            while (reader.hasNext()) {
                reader.beginObject()
                parse(reader, index)?.let {
                    list.add(it)
                }
                reader.endObject()
                index++
            }

            reader.endArray()
        }

        return list
    }

    companion object {

        private const val ID = "id"
        private const val NAME = "name"
        private const val CATEGORY_ID = "categoryId"
    }
}
