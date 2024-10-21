import androidx.compose.ui.window.ComposeUIViewController
import com.hardihood.two_square_game.App
import com.hardihood.two_square_game.core.appModules
import com.hardihood.two_square_game.core.di.getSharedModules
import com.multiplatform.lifecycle.LifecycleTracker
import com.multiplatform.lifecyle.LifecycleComposeUIVCDelegate
import org.koin.core.context.startKoin
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    val lifecycleTracker = LifecycleTracker()

    startKoin {
        modules(getSharedModules() + appModules)
    }
    return ComposeUIViewController({
        delegate = LifecycleComposeUIVCDelegate(lifecycleTracker)
    }) {

        App()
    }
}
