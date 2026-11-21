plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.mianalisisforense"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.mianalisisforense"
        minSdk = 24
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
    implementation(libs.androidx.appcompat)
}
