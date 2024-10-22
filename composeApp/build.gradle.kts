import org.jetbrains.compose.ExperimentalComposeLibrary

plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose)
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.buildConfig)
    alias(libs.plugins.google.services)
    alias(libs.plugins.google.firebase.crashlytics)
}

kotlin {
    jvmToolchain(17)
    androidTarget {
        //https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-test.html
        //   instrumentedTestVariant.sourceSetTree.set(KotlinSourceSetTree.test)
    }

//    jvm()
//
//    js {
//        browser()
//        binaries.executable()
//    }

//    wasmJs {
//        browser()
//        binaries.executable()
//    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {

        it.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true

        }

    }

    sourceSets {

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.kermit)
//            implementation(libs.kotlinx.coroutines.core)
//            implementation(libs.ktor.client.core)
//            implementation(libs.ktor.client.content.negotiation)
//            implementation(libs.ktor.client.serialization)
//            implementation(libs.ktor.client.logging)
//            implementation(libs.kotlinx.serialization.json)
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
//            implementation(libs.coil)
//            implementation(libs.coil.network.ktor)


            implementation(libs.voyager.navigator)
            implementation(libs.voyager.screenmodel)
            implementation(libs.voyager.koin)


            implementation(libs.compottie)
            implementation(libs.composesearchabledropdown)
            implementation(libs.kadmob)


            implementation(project(":core"))
            implementation(libs.compose.webview.multiplatform)

            implementation(compose.materialIconsExtended)
//            implementation(libs.kyoutube)
            implementation("network.chaintech:compose-multiplatform-media-player:1.0.24")

            implementation(libs.compose.toast)

            implementation(libs.kfirebase.core)
            implementation(libs.kfirebase.analytics)
            implementation(libs.kfirebase.crashlytics)
//            implementation(libs.kfirebase.database)
            implementation("dev.gitlive:firebase-database:2.1.0")

            // api(libs.compose.multiplatform.lifecycle.tracker)
            implementation(libs.lifecycle.compose)



        }

        commonTest.dependencies {
            implementation(kotlin("test"))
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.uiTest)
            implementation(libs.kotlinx.coroutines.test)
        }

        androidMain.dependencies {
            implementation(compose.uiTooling)
            implementation(libs.androidx.activityCompose)
            implementation(libs.kotlinx.coroutines.android)

            //  implementation(libs.androidx.lifecycle.runtime.ktx)

//            implementation(libs.ktor.client.okhttp)
        }

//        jvmMain.dependencies {
//            implementation(compose.desktop.currentOs)
//            implementation(libs.kotlinx.coroutines.swing)
//            implementation(libs.ktor.client.okhttp)
//        }
//
//        jsMain.dependencies {
//            implementation(compose.html.core)
//            implementation(libs.ktor.client.js)
//        }

        iosMain.dependencies {
//            implementation(libs.ktor.client.darwin)

        }

    }
}

android {
    namespace = "com.hardihood.two_square_game"
    compileSdk = 35


    defaultConfig {
        minSdk = 24
        targetSdk = 35

        applicationId = "com.hardihood.two_square_game"
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    }
}

//https://developer.android.com/develop/ui/compose/testing#setup
dependencies {
    androidTestImplementation(libs.androidx.uitest.junit4)
    debugImplementation(libs.androidx.uitest.testManifest)
    //temporary fix: https://youtrack.jetbrains.com/issue/CMP-5864
    androidTestImplementation("androidx.test:monitor") {
        version { strictly("1.6.1") }
    }
}

//compose.desktop {
//    application {
//        mainClass = "MainKt"
//
//        nativeDistributions {
//            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
//            packageName = "Choose two squares"
//            packageVersion = "1.0.0"
//
//            linux {
//                iconFile.set(project.file("desktopAppIcons/LinuxIcon.png"))
//            }
//            windows {
//                iconFile.set(project.file("desktopAppIcons/WindowsIcon.ico"))
//            }
//            macOS {
//                iconFile.set(project.file("desktopAppIcons/MacosIcon.icns"))
//                bundleID = "com.hardihood.two_square_game.desktopApp"
//            }
//        }
//    }
//}

buildConfig {
    // BuildConfig configuration here.
    // https://github.com/gmazzo/gradle-buildconfig-plugin#usage-in-kts

    buildConfigField(
        "String",
        "PRIVACY_URL",
        "\"https://mesho.linkie.tech/two_square_game/privacy_policy/privacy_policy.html\""
    )

    sourceSets {
        getByName("androidMain") {
            buildConfigField(
                "String",
                "interstitialAd",
                "\"ca-app-pub-7284367511062855/7574670867\""
            )
            buildConfigField("String", "bannerAd", "\"ca-app-pub-7284367511062855/6312687941\"")
        }
        getByName("iosMain") {

            buildConfigField(
                "String",
                "interstitialAd",
                "\"ca-app-pub-7284367511062855/1974850970\""
            )
            buildConfigField("String", "bannerAd", "\"ca-app-pub-7284367511062855/9759925938\"")

        }
    }

}
