package com.hardihood.two_square_game.core.services

import io.github.kadmob.KAdmobInterstitialAd

expect object AdServices {
    var interstitialAd: KAdmobInterstitialAd
    var bannerId: String

    fun showInterstitialAd()

}