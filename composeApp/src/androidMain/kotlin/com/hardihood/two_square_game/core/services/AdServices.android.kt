package com.hardihood.two_square_game.core.services

import choose_two_squares.composeApp.AndroidMainBuildConfig
import io.github.kadmob.KAdmobInterstitialAd

actual object AdServices {
    actual var interstitialAd: KAdmobInterstitialAd = KAdmobInterstitialAd()



    actual fun showInterstitialAd() {
        interstitialAd.showInterstitialAd()
    }

    actual var bannerId: String = AndroidMainBuildConfig.bannerAd
    actual fun loadInterstitialAd() {
        interstitialAd.loadInterstitialAd(AndroidMainBuildConfig.interstitialAd)

    }

}