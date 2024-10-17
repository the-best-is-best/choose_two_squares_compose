package com.hardihood.two_square_game.core

import android.app.Activity
import java.lang.ref.WeakReference

object AndroidManager {
    private var activity: WeakReference<Activity?> = WeakReference(null)

    internal fun getActivity(): Activity {
        return activity.get()!!
    }

    fun initialization(activity: Activity) {
        AndroidManager.activity = WeakReference(activity)


    }
}