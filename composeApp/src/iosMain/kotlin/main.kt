import androidx.compose.ui.window.ComposeUIViewController
import com.hardihood.two_square_game.App
import com.hardihood.two_square_game.core.appModules
import org.koin.core.context.startKoin
import platform.UIKit.UIViewController
import com.hardihood.two_square_game.core.di.getSharedModules

fun MainViewController(): UIViewController {
    startKoin {
        modules(getSharedModules() + appModules)
    }
    return ComposeUIViewController { App() }
}
