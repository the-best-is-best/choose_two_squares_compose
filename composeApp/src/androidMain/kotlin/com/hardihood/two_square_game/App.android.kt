package com.hardihood.two_square_game

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.hardihood.two_square_game.core.AndroidManager
import com.hardihood.two_square_game.core.appModules
import com.hardihood.two_square_game.core.di.getSharedModules
import io.github.kadmob.AndroidKAdmob
import org.koin.core.context.startKoin

class AppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        AndroidManager.initialization(this)
        AndroidKAdmob.initialization(this)

        startKoin {
            modules(getSharedModules() + appModules)
        }
        setContent { App() }
    }

}

@Preview
@Composable
fun AppPreview() {
    App()
}
