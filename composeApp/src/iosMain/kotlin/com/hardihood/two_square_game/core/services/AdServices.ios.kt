package com.hardihood.two_square_game.core.services

import io.github.kadmob.KAdmobInterstitialAd

actual object AdServices {
    actual var interstitialAd: KAdmobInterstitialAd = KAdmobInterstitialAd()

    init {
        interstitialAd.loadInterstitialAd("ca-app-pub-7284367511062855/1974850970")
    }

    actual fun show() {
        interstitialAd.showInterstitialAd()
    }
}