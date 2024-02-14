package com.example.natube

import android.animation.ValueAnimator
import android.view.View

object AnimationView {
    fun shakeView(view : View){


        val animator = ValueAnimator.ofFloat(-15f, 15f, 0f)
        animator.duration = 500
        animator.addUpdateListener { animation ->
            val value = animation.animatedValue as Float
            view.rotation = value
        }
        animator.start()

    }
}