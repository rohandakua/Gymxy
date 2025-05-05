plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.gms.google.services)

    // for hilt
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")

}


android {
    namespace = "com.gymxy.gymxyone"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.gymxy.gymxyone"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
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
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.firebase.auth)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.play.services.auth)   // for navigation
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    /**
     * for all the animation we are using the DotLottieAndroid library for this animation
     * visit for more info. - https://github.com/LottieFiles/dotlottie-android
     */
    implementation(libs.dotlottie.android)   // version used = 0.5.0     can also check in libs.versions.toml file for accurate info.

    /**
     * for hilt
     * visit for more info. - https://developer.android.com/training/dependency-injection/hilt-android#kts
     */
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation (libs.androidx.hilt.navigation.compose)


    /**
     * for Credential Manager
     * visit for more info. - https://developer.android.com/identity/sign-in/credential-manager-siwg
     *
     * for latest version of the com.google.android.libraries.identity.googleid:googleid visit https://mvnrepository.com/artifact/com.google.android.libraries.identity.googleid/googleid
     */
    implementation (libs.androidx.credentials)
    implementation (libs.androidx.credentials.play.services.auth)
    implementation (libs.googleid)


    /**
     * for google firestore
     */
    implementation(libs.firebase.firestore)


}
// Allow references to generated code
kapt {
    correctErrorTypes = true
}