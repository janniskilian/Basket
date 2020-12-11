package de.janniskilian.basket.core.util.android

import android.content.Intent
import android.speech.RecognizerIntent
import java.util.*

fun createSpeechInputIntent(): Intent =
    Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
        putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        putExtra(
            RecognizerIntent.EXTRA_LANGUAGE,
            Locale.getDefault().language
        )
    }

fun getSpeechInputResult(data: Intent?): String? =
    data
        ?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
        ?.firstOrNull()

const val REQ_SPEECH_INPUT = 101
