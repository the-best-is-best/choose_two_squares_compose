package com.hardihood.two_square_game

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.tooling.preview.Preview
import com.hardihood.two_square_game.core.AndroidManager
import com.hardihood.two_square_game.core.appModules
import com.hardihood.two_square_game.core.di.getSharedModules
import com.multiplatform.lifecycle.LifecycleTracker
import com.multiplatform.lifecycle.LocalLifecycleTracker
import com.multiplatform.lifecyle.AndroidLifecycleEventObserver
import io.github.firebase_analytics.AndroidKFirebaseAnalytics
import io.github.firebase_core.AndroidKFirebaseCore
import io.github.firebase_crashlytics.KFirebaseCrashlytics
import io.github.kadmob.AndroidKAdmob
import org.koin.core.context.startKoin

class AppActivity : ComponentActivity() {
    private val lifecycleTracker = LifecycleTracker()
    private val observer = AndroidLifecycleEventObserver(lifecycleTracker)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        AndroidManager.initialization(this)
        AndroidKAdmob.initialization(this)
        AndroidKFirebaseCore.initialize(this)
        AndroidKFirebaseAnalytics.initialization(this)
        KFirebaseCrashlytics().setCrashlyticsCollectionEnabled(true)
        lifecycle.addObserver(observer)
        startKoin {
            modules(getSharedModules() + appModules)
        }
        setContent {
            CompositionLocalProvider(LocalLifecycleTracker provides lifecycleTracker) {
                App()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(observer)
    }
}

@Preview
@Composable
fun AppPreview() {
    App()
}
