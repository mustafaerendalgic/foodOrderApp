package com.example.bitirmeprojesi.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.data.entity.Yemekler
import com.example.bitirmeprojesi.ui.fragment.DetayFragmentDirections
import com.example.bitirmeprojesi.ui.fragment.FavoriFragmentDirections
import com.example.bitirmeprojesi.ui.viewmodel.FavoriViewModel
import com.example.bitirmeprojesi.utils.gecisYap


class FavoriVH(item: View): RecyclerView.ViewHolder(item){
    val resim: ImageView
    val isim: TextView
    val anim: LottieAnimationView

    init {
        resim = item.findViewById<ImageView>(R.id.favoriItemResim)
        isim = item.findViewById<TextView>(R.id.favoriItemIsim)
        anim = item.findViewById<LottieAnimationView>(R.id.favoriItemAnimasyon)
    }
}

class FavoriAdapter(var list: List<Yemekler>, val ctx: Context, val viewModel: FavoriViewModel) : RecyclerView.Adapter<FavoriVH>() {

    private val baseURL = "http://kasimadalan.pe.hu/yemekler/resimler/"

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriVH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.favori_tasarim, parent, false)
        return FavoriVH(v)
    }

    override fun onBindViewHolder(
        holder: FavoriVH,
        position: Int
    ) {
        val item = list[position]
        Glide.with(ctx).load(baseURL + item.yemek_resim_adi).into(holder.resim)
        holder.isim.text = item.yemek_adi
        holder.anim.playAnimation()

        holder.anim.setOnClickListener {
            if(viewModel.list[item.yemek_adi] == null ) {
                holder.anim.speed = 1.0f
                viewModel.favorilereEkle(item, item.yemek_adi)
                holder.anim.playAnimation()
            }
            else{
                viewModel.favorilerdenSil(item.yemek_adi)
                holder.anim.progress = 0f
                holder.anim.cancelAnimation()
            }
        }

        holder.itemView.setOnClickListener {
            val gecis = FavoriFragmentDirections.favoriToDetay(item)
            Navigation.gecisYap(it, gecis)

        }

    }

    fun updateList(list2: List<Yemekler>){
        list = list2
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

}