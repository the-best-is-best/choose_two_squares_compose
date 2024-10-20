package com.hardihood.two_square_game.core.services

import Choose_two_squares.composeApp.AndroidMainBuildConfig
import Choose_two_squares.composeApp.BuildConfig
import io.github.kadmob.KAdmobInterstitialAd

actual object AdServices {
    actual var interstitialAd: KAdmobInterstitialAd = KAdmobInterstitialAd()

    init {
        interstitialAd.loadInterstitialAd(AndroidMainBuildConfig.interstitialAd)
    }

    actual fun showInterstitialAd() {
        interstitialAd.showInterstitialAd()
    }

    actual var bannerId: String = AndroidMainBuildConfig.bannerAd

}