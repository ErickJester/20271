plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.programaciongrafica"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.programaciongrafica"
        // La guía pide Minimum SDK = API 31.
        minSdk = 31
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.constraintlayout)
    implementation(libs.material)
}
