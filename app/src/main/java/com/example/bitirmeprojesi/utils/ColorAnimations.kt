package com.example.bitirmeprojesi.utils

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.media.Image
import android.view.View
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.ui.adapter.YemekAdapter

fun ColorAnimationForBar(colorFrom: Int, colorTo: Int, item: CardView){

    val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo)
    colorAnimation.duration = 500
    colorAnimation.addUpdateListener {
        (item as CardView).setCardBackgroundColor(it.animatedValue as Int)
    }

    colorAnimation.start()
}

fun ColorAnimationForAdapter(colorFrom: Int, colorTo: Int, arc: ImageView){
    val drawable = ContextCompat.getDrawable(arc.context, R.drawable.arc)?.mutate()
    val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo)
    colorAnimation.duration = 500
    arc.setImageDrawable(drawable)
    colorAnimation.addUpdateListener {
        arc.drawable.setTint(it.animatedValue as Int)
        arc.setImageDrawable(drawable)
    }
    colorAnimation.start()
}

fun ColorAnimationForBackground(item: View, colorFrom: Int, colorTo: Int){
    val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo)
    colorAnimation.duration = 500
    colorAnimation.addUpdateListener {
        item.setBackgroundColor(it.animatedValue as Int)
    }
    colorAnimation.start()
}
