android {
    defaultConfig {
        applicationId = Conf.applicationId
        vectorDrawables.useSupportLibrary = true
    }
}

dependencies {
    implementation(Deps.AndroidX.core)
    implementation(Deps.AndroidX.appcompat)
    implementation(Deps.AndroidX.livedata)
    implementation(Deps.AndroidX.viewmodel)
    implementation(Deps.AndroidX.lifecycleExt)
    implementation(Deps.AndroidX.constraintlayout)
    implementation(Deps.AndroidX.recyclerview)
    implementation(Deps.AndroidX.preference)

    implementation(Deps.material)

    implementation(project(":lists"))
    implementation(project(":groups"))
    implementation(project(":articles"))
    implementation(project(":categories"))

    androidTestImplementation("androidx.test.espresso:espresso-core:3.1.1")
    androidTestImplementation("androidx.test.espresso:espresso-contrib:3.1.1")
}
