plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("name.remal.check-dependency-updates") version Versions.Util.CheckDependencyUpdates
}

android {
    compileSdk = Application.compileSdk

    defaultConfig {
        minSdk = Application.minSdk
        targetSdk = Application.targetSdk
        versionCode = Application.versionCode
        versionName = Application.versionName
        multiDexEnabled = true
        setProperty("archivesBaseName", "$versionName ($versionCode)")

        kapt {
            arguments {
                arg("room.schemaLocation", "$projectDir/schemas")
            }

            correctErrorTypes = true
        }

        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField("long", "TIMESTAMP", "${System.currentTimeMillis()}L")
    }

    buildFeatures {
        compose = true
    }

    buildTypes {
        release {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
        }
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.Compose.Master
    }

    sourceSets {
        getByName("main").run {
            java.srcDirs("src/main/kotlin")
        }
    }

    compileOptions {
        sourceCompatibility = Application.sourceCompat
        targetCompatibility = Application.targetCompat
    }

    kotlinOptions {
        jvmTarget = Application.jvmTarget
    }
}

dependencies {
    implementation("com.github.skydoves:landscapist-coil:1.2.8") {
        exclude(group = "androidx.appcompat", module = "appcompat")
        exclude(group = "androidx.appcompat", module = "appcompat-resources")
    }

    Dependencies.debug.forEach(::debugImplementation)
    Dependencies.essential.forEach(::implementation)
    Dependencies.network.forEach(::implementation)
    Dependencies.ui.forEach(::implementation)
    Dependencies.util.forEach(::implementation)
    Dependencies.compose.forEach(::implementation)
    Dependencies.hilt.forEach(::implementation)
    Dependencies.room.forEach(::implementation)
    Dependencies.compiler.forEach(::kapt)
}
