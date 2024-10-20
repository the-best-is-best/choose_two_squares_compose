package com.hardihood.two_square_game.core.services

import Choose_two_squares.composeApp.BuildConfig
import Choose_two_squares.composeApp.IosMainBuildConfig
import io.github.kadmob.KAdmobInterstitialAd

actual object AdServices {
    actual var interstitialAd: KAdmobInterstitialAd = KAdmobInterstitialAd()

    init {
        interstitialAd.loadInterstitialAd(IosMainBuildConfig.interstitialAd)
    }

    actual fun showInterstitialAd() {
        interstitialAd.showInterstitialAd()
    }

    actual var bannerId: String = IosMainBuildConfig.bannerAd
}