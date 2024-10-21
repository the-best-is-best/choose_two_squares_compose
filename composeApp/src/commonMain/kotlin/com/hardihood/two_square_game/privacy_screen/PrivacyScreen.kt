package com.hardihood.two_square_game.privacy_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import choose_two_squares.composeApp.BuildConfig
import choose_two_squares.composeapp.generated.resources.Res
import choose_two_squares.composeapp.generated.resources.privacy_policy
import com.hardihood.two_square_game.components.MyText
import com.hardihood.two_square_game.components.MyTextAttribute
import com.hardihood.two_square_game.core.AppColors
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewState

class PrivacyScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val privacyUrl = rememberWebViewState(url = BuildConfig.PRIVACY_URL)

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(AppColors.secondColor)
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically

            ) {
                IconButton(onClick = {
                    navigator.pop()
                }) {
                    Icon(
                        Icons.Default.ArrowBackIosNew,
                        contentDescription = "Back",
                        tint = Color.White
                    )

                }
                Spacer(modifier = Modifier.width(25.dp))
                MyText(
                    attribute = MyTextAttribute(
                        textAlign = TextAlign.Center,
                        text = Res.string.privacy_policy,

                        fontSize = 25.sp,
                        color = Color.White
                    )
                )
            }
            WebView(privacyUrl)

        }
    }
}