package com.pieterv.inthedetails

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun Animation(anim: Int) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(anim))
    LottieAnimation(
        composition,
        isPlaying = true,
        iterations = Int.MAX_VALUE
    )
}