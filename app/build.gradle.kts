plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization) apply true
    alias(libs.plugins.hilt.android) apply true
    alias(libs.plugins.firebase) apply true
    id("kotlin-kapt")
}

android {
    namespace = "com.coordinadora.coordinadoraapp"
    compileSdk = 34

    signingConfigs {
        create("release") {
            storeFile = file(System.getenv("KEYSTORE_FILE") ?: "release.keystore")
            storePassword = System.getenv("KEYSTORE_PASSWORD") ?: ""
            keyAlias = System.getenv("KEY_ALIAS") ?: ""
            keyPassword = System.getenv("KEY_PASSWORD") ?: ""
        }
    }

    defaultConfig {
        applicationId = "com.coordinadora.coordinadoraapp"
        minSdk = 24
        targetSdk = 34
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
            // Agrega esta línea para usar la configuración de firma
            signingConfig = signingConfigs.getByName("release")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Navigation
    implementation(libs.navigation.compose)

    // Serialization
    implementation(libs.kotlinx.serialization)

    // Network
    implementation(libs.volley)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation (libs.hilt.navigation.compose)

    // Coil
    implementation(libs.coil.compose)

    // Maps
    implementation(libs.maps.compose)

    // Room
    implementation(libs.room)
    kapt(libs.room.compiler)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore)

    // TEST

    testImplementation (libs.junit)
    testImplementation (libs.mockk)
    testImplementation (libs.kotlinx.coroutines.test)

}

kapt {
    correctErrorTypes = true
}
