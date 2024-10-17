import androidx.compose.ui.window.ComposeUIViewController
import com.hardihood.two_square_game.App
import com.hardihood.two_square_game.core.appModules
import org.koin.core.context.startKoin
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    startKoin {
        modules(appModules)
    }
    return ComposeUIViewController { App() }
}
