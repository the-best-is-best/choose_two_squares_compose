package com.hardihood.two_square_game.home_screen.components

import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RichTooltip
import androidx.compose.material3.Surface
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import choose_two_squares.composeapp.generated.resources.Res
import choose_two_squares.composeapp.generated.resources.dev_2
import choose_two_squares.composeapp.generated.resources.menu_title
import choose_two_squares.composeapp.generated.resources.spacial_thanks
import com.hardihood.two_square_game.components.MyText
import com.hardihood.two_square_game.components.MyTextAttribute
import com.hardihood.two_square_game.core.AppColors
import com.hardihood.two_square_game.core.util.closeApp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAppBar() {
    val stateTooltip = rememberTooltipState()
    val scope = rememberCoroutineScope()
    Surface(
        modifier = Modifier.shadow(elevation = 14.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(AppColors.secondColor)
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Filled.Close,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.padding(15.dp).clickable {
                    closeApp()
                })
            MyText(
                attribute = MyTextAttribute(
                    textAlign = TextAlign.Center,
                    text = Res.string.menu_title,

                    fontSize = 16.sp,
                    color = Color.White
                )
            )

            TooltipBox(
                positionProvider = TooltipDefaults.rememberRichTooltipPositionProvider(),
                tooltip = {
                    RichTooltip(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        colors = TooltipDefaults.richTooltipColors(
                            containerColor = AppColors.secondColor
                        ),
                        title = {
                            MyText(
                                attribute = MyTextAttribute(
                                    text = Res.string.spacial_thanks,
                                    fontSize = 25.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            )
                        },
                        text = {
                            Column {


                                MyText(
                                    attribute = MyTextAttribute(
                                        text = Res.string.dev_2,
                                        fontSize = 20.sp,
                                        color = Color.White
                                    )
                                )


//                            Row {
//                                MyText(
//                                    attribute = MyTextAttribute(
//                                        text = SharedRes.strings.multiplayer_version_text.resourceId,
//                                        fontSize = 20.sp,
//                                        color = Color.White
//                                    )
//                                )
//                                Spacer(modifier = Modifier.weight(1f))
//                                MyText(
//                                    attribute = MyTextAttribute(
//                                        text = SharedRes.strings.multiplayer_version.resourceId,
//                                        fontSize = 20.sp,
//                                        color = Color.White
//                                    )
//                                )
//                            }
                            }
                        }
                    )
                },
                state = stateTooltip
            ) {
                IconButton(onClick = {
                    scope.launch {
                        stateTooltip.show(MutatePriority.Default)
                    }
                }) {
                    Icon(Icons.Default.Info, contentDescription = "info", tint = Color.White)
                }
            }

        }
    }
}