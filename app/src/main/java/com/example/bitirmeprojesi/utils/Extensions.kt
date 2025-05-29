package com.example.bitirmeprojesi.utils

import android.view.View
import androidx.navigation.NavDirections
import androidx.navigation.Navigation

fun Navigation.gecisYap(item: View, id: Int){
    findNavController(item).navigate(id)
}

fun Navigation.gecisYap(item: View, id: NavDirections){
    findNavController(item).navigate(id)
}