package com.hardihood.two_square_game.components

//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.hardihood.two_square_game.core.AppColors
import org.jetbrains.compose.resources.StringResource


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AskQuestionDialog(
    question: StringResource,
    btn1Click: () -> Unit,
    btn2Click: () -> Unit,
    btn3Click: (() -> Unit)? = null,
    btn1Text: StringResource,
    btn2Text: StringResource,
    btn3Text: StringResource? = null,
    onDismissRequest: () -> Unit,
) {
    BasicAlertDialog(
        properties = DialogProperties(),
        onDismissRequest = onDismissRequest,
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(AppColors.mainColor)
            .padding(20.dp),
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row {
                    MyText(
                        attribute = MyTextAttribute(
                            textAlign = TextAlign.Center,
                            text = question,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
                Spacer(Modifier.height(30.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(
                        16.dp,
                        Alignment.CenterHorizontally
                    ) // Adjusted spacing
                ) {
                    ElevatedButton(
                        modifier = Modifier.weight(.3f),
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AppColors.secondColor
                        ),
                        contentPadding = PaddingValues(),
                        onClick = btn1Click
                    ) {
                        MyText(
                            attribute = MyTextAttribute(
                                text = btn1Text,
                                fontSize = 20.sp
                            )
                        )
                    }
                    ElevatedButton(
                        modifier = Modifier.weight(.3f),
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AppColors.secondColor
                        ),
                        contentPadding = PaddingValues(),
                        onClick = btn2Click
                    ) {
                        MyText(
                            attribute = MyTextAttribute(
                                text = btn2Text,
                                fontSize = 20.sp
                            )
                        )
                    }
                    if (btn3Click != null) {
                        ElevatedButton(
                            modifier = Modifier.weight(.3f),
                            shape = RoundedCornerShape(20.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = AppColors.secondColor
                            ),
                            contentPadding = PaddingValues(),
                            onClick = btn3Click
                        ) {
                            MyText(
                                attribute = MyTextAttribute(
                                    text = btn3Text!!,
                                    fontSize = 20.sp
                                )
                            )
                        }
                    }
                }
            }
        }
    )
}
