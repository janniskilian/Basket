package de.janniskilian.basket.core

import androidx.datastore.preferences.core.preferencesKey

const val MIME_TYPE_TEXT_PLAIN = "text/plain"

const val NEWLINE = "\n"

const val REQ_SPEECH_INPUT = 101

val KEY_DEFAULT_DATA_IMPORTED = preferencesKey<Boolean>("KEY_DEFAULT_DATA_IMPORTED")
