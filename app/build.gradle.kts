plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.rust.plugin)
}

// NOTE: change these as desired ...
var selectedTokenizer = "gemma-3-4b-it.json"

android {
    namespace = "com.example.hfta"
    compileSdk = 36

    defaultConfig {
        ndkVersion = "29.0.13113456"
        applicationId = "com.example.hfta"
        minSdk = 29
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            buildConfigField("String", "SELECTED_TOKENIZER", "\"${selectedTokenizer}\"")
        }
        release {
            buildConfigField("String", "SELECTED_TOKENIZER", "\"${selectedTokenizer}\"")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_20
        targetCompatibility = JavaVersion.VERSION_20
    }
    kotlinOptions {
        jvmTarget = "20"
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
}

cargo {
    module = "../rs-hfta"
    libname = "hfta"
    prebuiltToolchains = true
    targets = listOf("arm64")
    profile = "release"
    targetDirectory = "src/main/jniLibs/"
    verbose = true
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
}