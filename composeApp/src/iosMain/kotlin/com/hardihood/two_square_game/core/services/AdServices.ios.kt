package com.hardihood.two_square_game.core.services

import choose_two_squares.composeApp.IosMainBuildConfig
import io.github.kadmob.KAdmobInterstitialAd

actual object AdServices {
    actual var interstitialAd: KAdmobInterstitialAd = KAdmobInterstitialAd()



    actual fun showInterstitialAd() {
        interstitialAd.showInterstitialAd()
    }

    actual var bannerId: String = IosMainBuildConfig.bannerAd
    actual fun loadInterstitialAd() {
        interstitialAd.loadInterstitialAd(IosMainBuildConfig.interstitialAd)
    }

}