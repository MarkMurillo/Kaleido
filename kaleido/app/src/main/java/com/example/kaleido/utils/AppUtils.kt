package com.example.kaleido.utils

import com.example.kaleido.R
import kotlin.random.Random

object AppUtils {
    private val seed = Random(System.currentTimeMillis())

    private val resArray = arrayOf(
        R.raw.alliances_button,
        R.raw.best_heart_button,
        R.raw.kaleidoscope,
        R.raw.kaleidoscope_loading_animation,
        R.raw.play_button,
        R.raw.social_media_marketing,
        R.raw.viking_button,
        R.raw.visuals,
    )

    fun getRandomLottieResId(currentRes: Array<Int> = emptyArray()): Int {
        val resPool = resArray.toList().minus(currentRes.toList())

        // Make sure they are different.
        return resPool[seed.nextInt(resPool.size)]
    }
}