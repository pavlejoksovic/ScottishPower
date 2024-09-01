plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.android.ksp)
    alias(libs.plugins.hilt.android.gradle)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "com.example.scottishpower"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.scottishpower"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.example.scottishpower.HiltTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField(
            "String",
            "BASE_URL",
            "\"${project.findProperty("BASE_URL")}\""
        )
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
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            // required to fix junit conflict
            merges += "META-INF/LICENSE.md"
            merges += "META-INF/LICENSE-notice.md"
            merges += "/META-INF/gradle/incremental.annotation.processors"
        }
    }

}

tasks.withType<Test> {
    useJUnitPlatform()
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

    implementation(libs.retrofit)
    implementation(libs.retrofit.gson.converter)
    implementation(libs.gson)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.hilt.android)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.io.coil.kt)
    implementation(libs.androidx.datastore)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
    testRuntimeOnly(libs.junit.jupiter.engine)
    ksp(libs.hilt.compiler)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    androidTestImplementation(libs.mockk.android)
    androidTestImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.hilt.android.compiler)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}