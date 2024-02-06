plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}




android {
    namespace = "com.pritikjain.easyfood"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.pritikjain.easyfood"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
         viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")


    // Kotlin // Navigation component
    val nav_version = "2.3.5"
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")

    // intuit
    implementation ("com.intuit.sdp:sdp-android:1.0.6")
    implementation ("com.intuit.ssp:ssp-android:1.0.6")

    //gif
    implementation("pl.droidsonroids.gif:android-gif-drawable:1.2.17")

    // RetroFit

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.3.0")

    // for adding the images and download it // Glide
    implementation ("com.github.bumptech.glide:glide:4.12.0")

    // videoModel mvvm
    val lifecycle_version = "2.4.0-rc01"
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")
    implementation ("androidx.lifecycle:lifecycle-extensions:2.2.0")
//    implementation ("androidx.arch.lifecycle:viewmodel:1.1.0")

    // Room Library for local Database
    val room_version = "2.6.1"

    implementation ("androidx.room:room-runtime:$room_version")
    annotationProcessor ("androidx.room:room-compiler:$room_version")

//    // To use Kotlin annotation processing tool (kapt)
    kapt("androidx.room:room-compiler:$room_version")
//    // To use Kotlin Symbol Processing (KSP)
//    ksp ("androidx.room:room-compiler:$room_version")

    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$room_version")

    // Kotlin Coroutines
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0")


    val hilt_version = "2.44"
    // Hilt : Dependency injection
    implementation("com.google.dagger:hilt-android:$hilt_version")
    kapt("com.google.dagger:hilt-android-compiler:$hilt_version")

    // TODO: Not sure about this fragment
    implementation("androidx.fragment:fragment-ktx:1.6.2")


     //  androidx.lifecycle:lifecycle-runtime:2.5.1@aar and: - androidx.core:core-ktx:1.9.0@aar  With the dependency: - androidx.lifecycle:lifecycle-common:[2.5.1] versus: - androidx.lifecycle:lifecycle-common:2.3.1  The project may not compile after adding these libraries. Would you like to add them anyway?
}

// Allow references to generated code for Hilt
kapt {
    correctErrorTypes = true
}