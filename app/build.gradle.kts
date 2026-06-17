plugins {
    id("com.android.application")
}

android {
    namespace = "io.github.pixelims"
    compileSdk = 35

    defaultConfig {
        applicationId = "io.github.pixelims"
        minSdk = 31
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }

    // FIX #4: explicit signingConfigs block so the release build can find the
    // debug keystore that the CI workflow generates (or that lives on the dev
    // machine).  The old code referenced signingConfigs["debug"] without ever
    // declaring it, which compiles locally only by luck (AGP injects a debug
    // config implicitly for the debug build type, but NOT for release).
    signingConfigs {
        named("debug") {
            storeFile = file("${System.getProperty("user.home")}/.android/debug.keystore")
            storePassword = "android"
            keyAlias = "androiddebugkey"
            keyPassword = "android"
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles("proguard-rules.pro")
            // Points to the explicitly declared signingConfig above
            signingConfig = signingConfigs["debug"]
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        buildConfig = true
    }

    packaging {
        resources {
            excludes += "**"
        }
    }

    lint {
        checkReleaseBuilds = false
    }

    dependenciesInfo {
        includeInApk = false
    }
}

dependencies {
    compileOnly(project(":stub"))
    implementation(libs.shizuku.provider)
    implementation(libs.shizuku.api)
    implementation(libs.hiddenapibypass)
}
