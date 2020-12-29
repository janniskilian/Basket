package de.janniskilian.basket.core.util.android

import android.content.Context
import android.content.Intent
import android.speech.RecognizerIntent
import androidx.activity.result.contract.ActivityResultContract
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

class SpeechInputResultContract : ActivityResultContract<Unit, String?>() {

    override fun createIntent(context: Context, input: Unit?): Intent =
        createSpeechInputIntent()

    override fun parseResult(resultCode: Int, intent: Intent?): String? =
        intent
            ?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            ?.firstOrNull()
}

const val REQ_SPEECH_INPUT = 101
