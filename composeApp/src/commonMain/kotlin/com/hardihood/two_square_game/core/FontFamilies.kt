package com.hardihood.two_square_game.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import choose_two_squares.composeapp.generated.resources.Montserrat_Bold
import choose_two_squares.composeapp.generated.resources.Montserrat_Medium
import choose_two_squares.composeapp.generated.resources.Montserrat_Regular
import choose_two_squares.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.Font

object FontFamilies {
    @Composable
    fun getMontserrat(): FontFamily {
        return FontFamily(
            Font(Res.font.Montserrat_Bold, weight = FontWeight.Bold),
            Font(Res.font.Montserrat_Medium, weight = FontWeight.Medium),
            Font(Res.font.Montserrat_Regular, weight = FontWeight.Normal)
        )

    }
}