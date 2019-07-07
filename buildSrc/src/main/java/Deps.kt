@Suppress("MayBeConstant")
object Deps {

    object Kotlin {
        val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${V.kotlin}"
        val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${V.coroutines}"
        val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${V.coroutines}"
    }

    object AndroidX {
        val core = "androidx.core:core-ktx:1.1.0-rc02"
        val appcompat = "androidx.appcompat:appcompat:1.1.0-rc01"
        val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:${V.lifecycle}"
        val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${V.lifecycle}"
        val lifecycleExt = "androidx.lifecycle:lifecycle-extensions:${V.lifecycle}"
        val navFragment = "androidx.navigation:navigation-fragment-ktx:${V.nav}"
        val navUi = "androidx.navigation:navigation-ui-ktx:${V.nav}"
        val roomRuntime = "androidx.room:room-runtime:${V.room}"
        val roomKtx = "androidx.room:room-ktx:${V.room}"
        val roomCompiler = "androidx.room:room-compiler:${V.room}"
        val constraintlayout = "androidx.constraintlayout:constraintlayout:1.1.3"
        val recyclerview = "androidx.recyclerview:recyclerview:1.0.0"
        val preference = "androidx.preference:preference:1.1.0-rc01"
    }

    val material = "com.google.android.material:material:1.1.0-alpha07"

    val timber = "com.jakewharton.timber:timber:4.7.1"

    object LeakCanary {
        val debug = "com.squareup.leakcanary:leakcanary-android:${V.leakCanary}"
        val fragment = "com.squareup.leakcanary:leakcanary-support-fragment:${V.leakCanary}"
        val release = "com.squareup.leakcanary:leakcanary-android-no-op:${V.leakCanary}"
    }

    object Testing {
        val junit = "junit:junit:4.12"
        val kotlinTest = "org.jetbrains.kotlin:kotlin-test-junit:${V.kotlin}"
        val mockito = "com.nhaarman.mockitokotlin2:mockito-kotlin:2.1.0"
        val robolectric = "org.robolectric:robolectric:4.3"
    }

    object AndroidTesting {
        val core = "androidx.test:core:1.2.0"
        val runner = "androidx.test:runner:1.2.0"
        val junit = "androidx.test.ext:junit:1.1.1"
        val rules = "androidx.test:rules:1.2.0"
        val espresso = "androidx.test.espresso:espresso-core:${V.espresso}"
        val espressoContrib = "androidx.test.espresso:espresso-contrib:${V.espresso}"
        val archCore = "androidx.arch.core:core-testing:2.0.1"
        val room = "androidx.room:room-testing:${V.room}"
    }

    object Plugins {
        val android = "com.android.tools.build:gradle:3.5.0-beta05"
        val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${V.kotlin}"
        val navSafeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:${V.nav}"
    }
}
