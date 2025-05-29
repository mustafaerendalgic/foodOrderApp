package com.example.bitirmeprojesi.ui.itemdecoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class CustomItemDecoration(val space: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if(parent.getChildAdapterPosition(view) % 2 == 0){
            outRect.left = space
            outRect.right = space / 2
        }
        else{
            outRect.left = space / 2
            outRect.right = space
        }
    }
}