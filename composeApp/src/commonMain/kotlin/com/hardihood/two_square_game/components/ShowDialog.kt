package com.hardihood.two_square_game.components

//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import choose_two_squares.composeapp.generated.resources.Res
import choose_two_squares.composeapp.generated.resources.ok_btn
import com.hardihood.two_square_game.core.AppColors
import com.hardihood.two_square_game.core.FontFamilies


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowDialog(
    onClick: () -> Unit,
    message: String,
) {
    BasicAlertDialog(
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        ),
        onDismissRequest = onClick,
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(AppColors.mainColor)
            .padding(20.dp),
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp) // Adjusted padding for a more balanced layout
            ) {
                Text(
                    text = message,
                    textAlign = TextAlign.Center,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontFamily = FontFamilies.getMontserrat(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp) // Balanced padding for text
                )

                Spacer(Modifier.height(30.dp))

                ElevatedButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp), // Adjusted padding around the button
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppColors.secondColor
                    ),
                    contentPadding = PaddingValues(),
                    onClick = onClick
                ) {
                    MyText(
                        attribute = MyTextAttribute(
                            text = Res.string.ok_btn,
                            fontSize = 20.sp
                        )
                    )
                }
            }
        }
    )
}
