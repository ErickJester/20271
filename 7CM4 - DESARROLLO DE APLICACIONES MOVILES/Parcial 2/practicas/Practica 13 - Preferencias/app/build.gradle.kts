plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.preferencias"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.preferencias"
        // 28: ImageDecoder para cargar la imagen de la galería reducida y con su rotación.
        minSdk = 28
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
