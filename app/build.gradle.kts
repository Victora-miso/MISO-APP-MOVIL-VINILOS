plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    alias(libs.plugins.navigation.safeargs)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.alphazetakapp.misoappvinilos"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.alphazetakapp.misoappvinilos"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.room.common)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.ui.text.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.gson)
    implementation(libs.converter.gson)
    implementation(libs.converter.scalars)

    // ViewModel
    implementation(libs.viewmodel)

    // LiveData
    implementation(libs.livedata)

    //Dagger Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    kapt(libs.androidx.room.compiler)

    //Navigation
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)

    // Glide for images
    implementation(libs.bumptech.glide)

    // Room
    implementation(libs.androidx.room.common)
    implementation(libs.androidx.room.ktx)
    implementation("androidx.room:room-runtime:${libs.versions.roomCommon.get()}")
    kapt("androidx.room:room-compiler:${libs.versions.roomCommon.get()}")
    implementation(kotlin("script-runtime"))

    //E2E
    androidTestImplementation ("androidx.test.espresso:espresso-core:$espressoVersion")
    // Core library
    androidTestImplementation("androidx.test:core:1.6.1")

    // AndroidJUnitRunner and JUnit Rules
    androidTestImplementation("androidx.test:runner:1.6.1")


    // Assertions
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    testImplementation("junit:junit:4.13.2") // Or your desired JUnit version
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test:rules:1.5.0")


    // Espresso dependencies
    androidTestImplementation( "androidx.test.espresso:espresso-core:$espressoVersion")
    androidTestImplementation ("androidx.test.espresso:espresso-contrib:3.4.0")


}

val espressoVersion = "3.6.1"

kapt {
    correctErrorTypes = true
}