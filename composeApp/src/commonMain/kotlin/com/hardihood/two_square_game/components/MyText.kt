package com.hardihood.two_square_game.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import choose_two_squares.composeapp.generated.resources.Montserrat_Regular
import choose_two_squares.composeapp.generated.resources.Res
import com.hardihood.two_square_game.core.FontFamilies
import org.jetbrains.compose.resources.FontResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

data class MyTextAttribute(
    val text: StringResource,
    val fontWeight: FontWeight = FontWeight.Normal,
    val font: FontResource = Res.font.Montserrat_Regular,
    val fontSize: TextUnit,
    val fontStyle: FontStyle = FontStyle.Normal,
    val color: Color = Color.White,
    val textAlign: TextAlign = TextAlign.Start,
    val lineHeight: TextUnit = TextUnit.Unspecified,
    val isRequired: Boolean = false,
)

@Composable
fun MyText(modifier: Modifier = Modifier, attribute: MyTextAttribute) {

    val displayText = if (attribute.isRequired) {
        "${stringResource(attribute.text)} *"
    } else {
        stringResource(attribute.text)
    }
    Text(
        modifier = modifier,
        text = displayText,
        lineHeight = attribute.lineHeight,
        fontFamily = FontFamilies.getMontserrat(),
        fontSize = attribute.fontSize,
        fontWeight = attribute.fontWeight,
        color = attribute.color,
        textAlign = attribute.textAlign
    )

}