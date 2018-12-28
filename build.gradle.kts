import groovy.lang.Closure
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath(Deps.Plugins.android)
        classpath(Deps.Plugins.kotlin)
        classpath(Deps.Plugins.navSafeArgs)
    }
}

plugins {
    id("com.github.ben-manes.versions") version "0.20.0"
    id("io.gitlab.arturbosch.detekt") version "1.0.0-RC12"
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += listOf(
            "-XXLanguage:+InlineClasses",
            "-progressive"
        )
    }
}

subprojects {
    if (name == "main") {
        apply(plugin = "com.android.application")
    } else {
        apply(plugin = "com.android.library")
    }
    apply(plugin = "kotlin-android")
    apply(plugin = "kotlin-android-extensions")
    apply(plugin = "androidx.navigation.safeargs")

    configure<com.android.build.gradle.TestedExtension> {
        buildToolsVersion(Conf.buildTools)
        compileSdkVersion(Conf.targetSdk)
        defaultConfig {
            minSdkVersion(Conf.minSdk)
            targetSdkVersion(Conf.targetSdk)
            versionCode = Conf.versionCode
            versionName = Conf.versionName
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
        buildTypes {
            getByName("debug") {
                multiDexEnabled = true
            }
            getByName("release") {
                isMinifyEnabled = true
                if (name == "main") {
                    isShrinkResources = true
                }
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }
        testOptions {
            unitTests.apply {
                isIncludeAndroidResources = true
            }
            animationsDisabled = true
        }
        lintOptions {
            isWarningsAsErrors = true
            isIgnoreTestSources = true
        }
    }

    apply(from = "$rootDir/androidExtensions.gradle")

    dependencies {
        val implementation by configurations
        val testImplementation by configurations
        val androidTestImplementation by configurations

        implementation(Deps.Kotlin.stdlib)
        implementation(Deps.Kotlin.coroutines)
        implementation(Deps.Kotlin.coroutinesAndroid)

        implementation(Deps.AndroidX.navFragment)
        implementation(Deps.AndroidX.navUi)

        implementation(Deps.timber)

        testImplementation(Deps.Testing.junit)
        testImplementation(Deps.Testing.kotlinTest)
        testImplementation(Deps.Testing.mockito)
        testImplementation(Deps.Testing.robolectric)

        androidTestImplementation(Deps.Testing.kotlinTest)
        androidTestImplementation(Deps.AndroidTesting.core)
        androidTestImplementation(Deps.AndroidTesting.runner)
        androidTestImplementation(Deps.AndroidTesting.junit)
        androidTestImplementation(Deps.AndroidTesting.rules)
        androidTestImplementation(Deps.AndroidTesting.archCore)
        androidTestImplementation(Deps.AndroidTesting.room)

        if (name != "core") {
            implementation(project(":core"))
        }
    }
}

detekt {
    input = files("$projectDir")
    config = files("$projectDir/detektConfig.yml")
    parallel = true
    filters = ".*test.*,.*/resources/.*,.*/tmp/.*"
}
