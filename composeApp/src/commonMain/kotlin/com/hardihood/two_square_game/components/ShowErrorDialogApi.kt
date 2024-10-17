package com.hardihood.two_square_game.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import choose_two_squares.composeapp.generated.resources.Res
import choose_two_squares.composeapp.generated.resources.cancel_btn
import choose_two_squares.composeapp.generated.resources.ok_btn
import choose_two_squares.composeapp.generated.resources.retry_btn
import com.hardihood.two_square_game.core.AppColors
import com.hardihood.two_square_game.core.FontFamilies
import io.github.alexzhirkevich.compottie.InternalCompottieApi
import io.github.handleerrorapi.Failure
import org.jetbrains.compose.resources.stringResource

@OptIn(InternalCompottieApi::class)
@Composable
fun ShowErrorDialogApi(
    visible: Boolean,
    message: Failure,
    cancel: () -> Unit,
    retry: (() -> Unit)? = null,
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
//        if (message.statusCode == 401 || message.statusCode == 403) {
//            Dialog(
//                properties = DialogProperties(
//                    dismissOnClickOutside = false,
//                    dismissOnBackPress = false
//                ),
//
//                onDismissRequest = {
//
//                }
//            ) {
//                Surface(shape = RoundedCornerShape(15.dp)) {
//                    Box(
//                        Modifier.background(Color.White)
//                    ) {
//                        Column(
//                            modifier = Modifier
//                                .padding(horizontal = 20.dp)
//                                .padding(bottom = 10.dp),
//
//                            ) {
//                            Spacer(modifier = Modifier.size(30.dp))
//                            Text(
//                                text = message.messages!!,
//                                fontSize = 20.sp,
//                                //    color = AppColors.white,
//                                textAlign = TextAlign.Center,
//                                fontWeight = FontWeight.Bold
//
//                            )
//                            Spacer(modifier = Modifier.size(30.dp))
//                            ElevatedButton(
//                                modifier = Modifier
//                                    .fillMaxWidth(),
//                                onClick = {
//
//                                    navigator.navigate(MenuScreenDestination()) {
//                                        navigator.popBackStack()
//                                    }
//
//
//                                }
//                            ) {
//                                Text(
//                                    text = SharedRes.strings.login.getString(context),
//                                    fontSize = 20.sp,
//                                    //  color = AppColors.white,
//                                    textAlign = TextAlign.Center,
//                                    fontWeight = FontWeight.Bold
//
//                                )
//                            }
//
//
//                        }
//                    }
//                }
//            }
//        } else {
        Dialog(

            onDismissRequest = { cancel() }
        ) {
            Surface(

                shape = RoundedCornerShape(15.dp) // Custom corner radius

            ) {
                Box(
                    Modifier.background(AppColors.mainColor)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .padding(bottom = 10.dp),

                        ) {
                        Spacer(modifier = Modifier.size(30.dp))
                        Text(
                            text = message.messages!!,
                            fontSize = 20.sp,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamilies.getMontserrat()

                        )
                        Spacer(modifier = Modifier.size(30.dp))
                        if (retry == null) {
                            ElevatedButton(
                                modifier = Modifier
                                    .fillMaxWidth(1f),

                                onClick = {
                                    cancel()
                                }
                            ) {
                                Text(
                                    text = stringResource(Res.string.ok_btn),
                                    fontSize = 20.sp,
                                    //  color = AppColors.white,
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamilies.getMontserrat()

                                )
                            }
                        } else {
                            Row {
                                ElevatedButton(
                                    colors = ButtonDefaults.elevatedButtonColors(
                                        containerColor = AppColors.secondColor

                                    ),
                                    modifier = Modifier
                                        .weight(.5f),
                                    onClick = {
                                        cancel()
                                    }
                                ) {
                                    Text(
                                        text = stringResource(Res.string.cancel_btn),
                                        fontSize = 20.sp,

                                        textAlign = TextAlign.Center,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = FontFamilies.getMontserrat()

                                    )
                                }


                                Spacer(modifier = Modifier.size(10.dp))
                                ElevatedButton(
                                    colors = ButtonDefaults.elevatedButtonColors(
                                        containerColor = AppColors.secondColor

                                    ),
                                    modifier = Modifier
                                        .weight(1f),
                                    onClick = {
                                        retry()


                                    }
                                ) {
                                    Text(
                                        text = stringResource(Res.string.retry_btn),
                                        fontSize = 20.sp,
                                        //  color = AppColors.white,
                                        textAlign = TextAlign.Center,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = FontFamilies.getMontserrat()

                                    )
                                }

                            }
                        }
                    }
                }
            }
        }
    }
}