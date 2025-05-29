package com.example.bitirmeprojesi.ui.adapter

import android.content.Context
import android.media.Image
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.Lottie
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.data.entity.Yemekler
import com.example.bitirmeprojesi.ui.fragment.AnaSayfaFragmentDirections
import com.example.bitirmeprojesi.ui.viewmodel.AnaSayfaViewModel
import com.example.bitirmeprojesi.ui.viewmodel.FavoriViewModel
import com.example.bitirmeprojesi.utils.ColorAnimationForAdapter
import com.example.bitirmeprojesi.utils.gecisYap

class anaSayfaViewHolder(item: View) : RecyclerView.ViewHolder(item){
    val yemekResim: ImageView
    val yemekAd: TextView
    val yemekFiyat: TextView
    val favori: LottieAnimationView
    val arc: ImageView
    init {
        yemekFiyat = item.findViewById<TextView>(R.id.kartFiyat)
        yemekAd = item.findViewById<TextView>(R.id.itemIsim)
        yemekResim = item.findViewById<ImageView>(R.id.itemResim)
        favori = item.findViewById<LottieAnimationView>(R.id.favorite)
        arc = item.findViewById<ImageView>(R.id.itemArc)
    }
}

class YemekAdapter(var list: List<Yemekler>, val context: Context, val viewModelFavori: FavoriViewModel) : RecyclerView.Adapter<anaSayfaViewHolder>() {

    private val arcList = mutableListOf<ImageView>()

    private val baseURL = "http://kasimadalan.pe.hu/yemekler/resimler/"

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): anaSayfaViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.kart_tasarim, parent, false)
        return anaSayfaViewHolder(v)
    }

    override fun onBindViewHolder(
        holder: anaSayfaViewHolder,
        position: Int
    ) {
        val item = list[position]
        Log.d("Adapter22", "Binding item: ${item.yemek_adi}")
        Glide.with(context).load(baseURL+item.yemek_resim_adi).into(holder.yemekResim)
        holder.yemekAd.text = item.yemek_adi
        holder.yemekFiyat.text = item.yemek_fiyat.toString() + "₺"

        if(!arcList.contains(holder.arc)){
            arcList.add(holder.arc)
        }

        holder.favori.setOnClickListener {
            if(viewModelFavori.list[item.yemek_adi] == null ) {
                holder.favori.speed = 1.0f
                viewModelFavori.favorilereEkle(item, item.yemek_adi)
                holder.favori.playAnimation()
            }
            else{
                viewModelFavori.favorilerdenSil(item.yemek_adi)
                holder.favori.progress = 0f
                holder.favori.cancelAnimation()
            }
        }

        holder.itemView.setOnClickListener {
            val gecis = AnaSayfaFragmentDirections.anaToDetay(item)
            Navigation.gecisYap(it, gecis)
        }

        if(viewModelFavori.list[item.yemek_adi] != null){
            holder.favori.playAnimation()
        }

    }

    fun updateColors(colorFrom: Int, colorTo: Int){
        for (arc in arcList) {
            ColorAnimationForAdapter(colorFrom, colorTo, arc)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateList(list2 : List<Yemekler>){
        list = list2
        notifyDataSetChanged()
    }
}