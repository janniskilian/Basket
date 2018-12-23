object Deps {

    object Kotlin {
        val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${V.kotlin}"
        val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${V.coroutines}"
        val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${V.coroutines}"
    }

    object AndroidX {
        val ktx = "androidx.core:core-ktx:1.0.1"
        val appcompat = "androidx.appcompat:appcompat:1.0.2"
        val lifecycle = "androidx.lifecycle:lifecycle-extensions:${V.lifecycle}"
        val navFragment = "android.arch.navigation:navigation-fragment-ktx:${V.nav}"
        val navUi = "android.arch.navigation:navigation-ui-ktx:${V.nav}"
        val roomRuntime = "androidx.room:room-runtime:${V.room}"
        val roomCoroutines = "androidx.room:room-coroutines:${V.room}"
        val roomCompiler = "androidx.room:room-compiler:${V.room}"
        val constraintlayout = "androidx.constraintlayout:constraintlayout:1.1.3"
        val recyclerview = "androidx.recyclerview:recyclerview:1.0.0"
        val preference = "androidx.preference:preference:1.0.0"
    }

    val material = "com.google.android.material:material:1.1.0-alpha02"

    val timber = "com.jakewharton.timber:timber:4.7.1"

    object LeakCanary {
        val debug = "com.squareup.leakcanary:leakcanary-android:${V.leakCanary}"
        val fragment = "com.squareup.leakcanary:leakcanary-support-fragment:${V.leakCanary}"
        val release = "com.squareup.leakcanary:leakcanary-android-no-op:${V.leakCanary}"
    }

    object Testing {
        val junit = "junit:junit:4.12"
        val kotlinTest = "org.jetbrains.kotlin:kotlin-test-junit:${V.kotlin}"
        val mockito = "com.nhaarman.mockitokotlin2:mockito-kotlin:2.0.0"
        val robolectric = "org.robolectric:robolectric:4.1"
    }

    object AndroidTesting {
        val core = "androidx.test:core:1.1.0"
        val runner = "androidx.test:runner:1.1.1"
        val junit = "androidx.test.ext:junit:1.1.0"
        val rules = "androidx.test:rules:1.1.1"
        val espresso = "androidx.test.espresso:espresso-core:${V.espresso}"
        val espressoContrib = "androidx.test.espresso:espresso-contrib:${V.espresso}"
        val livedata = "androidx.arch.core:core-testing:${V.lifecycle}"
        val room = "androidx.room:room-testing:${V.room}"
    }

    object Plugins {
        val android = "com.android.tools.build:gradle:3.4.0-alpha09"
        val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.11"
        val navSafeArgs = "android.arch.navigation:navigation-safe-args-gradle-plugin:1.0.0-alpha08"
    }
}
