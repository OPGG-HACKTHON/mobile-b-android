import org.gradle.api.JavaVersion

object Application {
    const val minSdk = 24
    const val targetSdk = 30
    const val compileSdk = 30
    const val jvmTarget = "11"
    const val versionCode = 1
    const val versionName = "opgg_mobile-b"

    val targetCompat = JavaVersion.VERSION_11
    val sourceCompat = JavaVersion.VERSION_11
}

object Versions {
    const val Gson = "2.8.8"

    object Essential {
        const val Kotlin = "1.5.21"
        const val CoreKtx = "1.6.0"
        const val Coroutines = "1.5.1"
        const val Gradle = "7.1.0-alpha05"
    }

    object Ui {
        const val ConstraintLayout = "1.0.0-beta01"
        const val Material = "1.4.0"
        const val LandscapistCoil = "1.3.4"
    }

    object Util {
        const val CheckDependencyUpdates = "1.5.0"
    }

    object Network {
        const val OkHttp = "4.9.1"
        const val Retrofit = "2.9.0"
    }

    object Hilt {
        const val Master = "2.38.1"
    }

    object Compose {
        const val Master = "1.0.1"
        const val Activity = "1.3.1"
    }

    object Debug {
        const val LeakCanary = "2.7"
    }

    object Lifecycle {
        const val Master = "2.2.0"
        const val Compose = "1.0.0-alpha07"
    }

    object Stomp {
        const val Master = "1.6.6"
    }

    object Rx {
        const val Java = "2.2.5"
        const val Android = "2.1.0"
    }
}

object Dependencies {
    const val json = "com.google.code.gson:gson:${Versions.Gson}"
    const val landscapistcoil =
        "com.github.skydoves:landscapist-coil:${Versions.Ui.LandscapistCoil}"

    val debug = listOf(
        "com.squareup.leakcanary:leakcanary-android:${Versions.Debug.LeakCanary}"
    )

    val network = listOf(
        "com.squareup.okhttp3:okhttp:${Versions.Network.OkHttp}",
        "com.squareup.retrofit2:retrofit:${Versions.Network.Retrofit}"
    )

    val networkutil = listOf(
        "com.squareup.okhttp3:logging-interceptor:${Versions.Network.OkHttp}",
        "com.squareup.retrofit2:converter-gson:${Versions.Network.Retrofit}",
    )

    val essential = listOf(
        "androidx.core:core-ktx:${Versions.Essential.CoreKtx}",
        "org.jetbrains.kotlin:kotlin-stdlib:${Versions.Essential.Kotlin}",
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.Essential.Coroutines}"
    )

    val ui = listOf(
        "androidx.constraintlayout:constraintlayout-compose:${Versions.Ui.ConstraintLayout}",
        "com.google.android.material:material:${Versions.Ui.Material}"
    )

    val hilt = listOf(
        "com.google.dagger:hilt-android:${Versions.Hilt.Master}",
    )

    val compose = listOf(
        "androidx.compose.ui:ui:${Versions.Compose.Master}",
        "androidx.compose.ui:ui-tooling:${Versions.Compose.Master}",
        "androidx.compose.compiler:compiler:${Versions.Compose.Master}",
        "androidx.compose.material:material:${Versions.Compose.Master}",
        "androidx.activity:activity-compose:${Versions.Compose.Activity}",
        "androidx.compose.runtime:runtime-livedata:${Versions.Compose.Master}"
    )

    val compiler = listOf(
        "com.google.dagger:hilt-android-compiler:${Versions.Hilt.Master}"
    )

    val lifecycle = listOf(
        "androidx.lifecycle:lifecycle-extensions:${Versions.Lifecycle.Master}",
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.Lifecycle.Master}",
        "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.Lifecycle.Compose}"
    )

    val stomp = listOf(
        "com.github.NaikSoftware:StompProtocolAndroid:${Versions.Stomp.Master}"
    )

    val rx = listOf(
        "io.reactivex.rxjava2:rxjava:${Versions.Rx.Java}",
        "io.reactivex.rxjava2:rxandroid:${Versions.Rx.Android}"
    )
}
