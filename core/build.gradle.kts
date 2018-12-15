import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    kotlin("kapt")
}

kapt {
    arguments {
        arg("room.schemaLocation", "$projectDir/schemas")
    }
}

dependencies {
    implementation(Deps.AndroidX.ktx)
    implementation(Deps.AndroidX.appcompat)
    implementation(Deps.AndroidX.lifecycle)
    implementation(Deps.AndroidX.constraintlayout)
    implementation(Deps.AndroidX.roomRuntime)
    implementation(Deps.AndroidX.roomCoroutines)
    kapt(Deps.AndroidX.roomCompiler)

    implementation(Deps.material)

    debugImplementation(Deps.LeakCanary.debug)
    debugImplementation(Deps.LeakCanary.fragment)
    releaseImplementation(Deps.LeakCanary.release)
}
