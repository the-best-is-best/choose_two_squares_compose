package com.hardihood.two_square_game

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.hardihood.two_square_game.core.appModules
import com.hardihood.two_square_game.core.di.getSharedModules
import io.github.firebase_analytics.AndroidKFirebaseAnalytics
import io.github.firebase_core.AndroidKFirebaseCore
import io.github.firebase_crashlytics.KFirebaseCrashlytics
import io.github.kadmob.AndroidKAdmob
import io.github.tbib.compose_check_for_update.AndroidCheckForUpdate
import io.github.tbib.compose_toast.AndroidLogoToast
import org.koin.core.context.startKoin

class AppActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        AndroidKAdmob.initialization(this)
        AndroidKFirebaseCore.initialize(this)
        AndroidKFirebaseAnalytics.initialization(this)
        KFirebaseCrashlytics().setCrashlyticsCollectionEnabled(true)
        AndroidLogoToast.initialization(this)
        AndroidCheckForUpdate.initialization(this)
        startKoin {
            modules(getSharedModules() + appModules)
        }
        setContent {
            App()
        }
    }


}

@Preview
@Composable
fun AppPreview() {
    App()
}
