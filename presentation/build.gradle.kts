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
    implementation(Dependencies.json)
    implementation(Dependencies.landscapistcoil) {
        exclude(group = "androidx.appcompat", module = "appcompat")
        exclude(group = "androidx.appcompat", module = "appcompat-resources")
    }

    implementation(project(":data"))
    implementation(project(":domain"))

    Dependencies.debug.forEach(::debugImplementation)
    Dependencies.essential.forEach(::implementation)
    Dependencies.network.forEach(::implementation)
    Dependencies.networkutil.forEach(::implementation)
    Dependencies.ui.forEach(::implementation)
    Dependencies.compose.forEach(::implementation)
    Dependencies.hilt.forEach(::implementation)
    Dependencies.lifecycle.forEach(::implementation)
    Dependencies.compiler.forEach(::kapt)
    Dependencies.stomp.forEach(::implementation)
    Dependencies.rx.forEach(::implementation)
}
