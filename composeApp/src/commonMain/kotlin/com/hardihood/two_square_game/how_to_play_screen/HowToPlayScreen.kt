package com.hardihood.two_square_game.how_to_play_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import choose_two_squares.composeapp.generated.resources.Res
import choose_two_squares.composeapp.generated.resources.how_to_play_text
import choose_two_squares.composeapp.generated.resources.how_to_play_title
import com.hardihood.two_square_game.components.MyText
import com.hardihood.two_square_game.components.MyTextAttribute
import com.hardihood.two_square_game.core.AppColors
import io.github.the_best_is_best.kyoutube.KYoutube

class HowToPlayScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        var componentSize by remember { mutableStateOf(IntSize.Zero) }
        val density = LocalDensity.current

        Column(modifier = Modifier
            .fillMaxSize()
            .onGloballyPositioned { coordinates ->
                // Capture the size of the parent container
                componentSize = coordinates.size
            }
        ) {
            // Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(AppColors.secondColor)
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navigator.pop() }) {
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
                        text = Res.string.how_to_play_title,
                        fontSize = 25.sp,
                        color = Color.White
                    )
                )
            }

            // Content
            LazyColumn(modifier = Modifier.padding(10.dp)) {
                val youtubeHeight =
                    with(density) { (componentSize.height * .25f).toDp() } // Calculate 50% of the parent height

                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .height(youtubeHeight) // Set a fixed height for the video player
                    ) {
                        KYoutube("51tBJRncZnI")
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    MyText(
                        attribute = MyTextAttribute(
                            textAlign = TextAlign.Center,
                            text = Res.string.how_to_play_text,
                            fontSize = 20.sp,
                            color = Color.White
                        )
                    )
                }
            }
        }
    }
}
