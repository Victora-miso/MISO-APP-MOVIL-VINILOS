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
}


kapt {
    correctErrorTypes = true
}