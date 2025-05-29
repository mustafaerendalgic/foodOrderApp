package com.example.bitirmeprojesi.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.data.entity.SepetYemekler
import com.example.bitirmeprojesi.data.entity.Yemekler
import com.example.bitirmeprojesi.ui.viewmodel.FavoriViewModel
import org.w3c.dom.Text
import java.time.format.TextStyle

class sepetVH(val item: View): RecyclerView.ViewHolder(item){
    val resim: ImageView
    val ad: TextView
    val fiyat: TextView
    val miktar: TextView
    init{
        resim = item.findViewById<ImageView>(R.id.sepetItemResim)
        ad = item.findViewById<TextView>(R.id.sepetItemIsim)
        fiyat = item.findViewById<TextView>(R.id.sepetItemFiyat)
        miktar = item.findViewById<TextView>(R.id.sepetMiktar)
    }
}

class SepetAdapter(var list: List<SepetYemekler>, val ctx: Context, ) : RecyclerView.Adapter<sepetVH>(){

    private val baseURL = "http://kasimadalan.pe.hu/yemekler/resimler/"

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): sepetVH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.sepet_kart_tasarim, parent, false)
        return sepetVH(v)
    }

    override fun onBindViewHolder(
        holder: sepetVH,
        position: Int
    ) {
        val item = list[position]
        Glide.with(ctx).load(baseURL + item.yemek_resim_adi).into(holder.resim)
        holder.ad.text = item.yemek_adi
        holder.fiyat.text = item.yemek_fiyat.toString() + "₺"
        holder.miktar.text = item.yemek_siparis_adet
        }

    fun listeyiGuncelle(list2: List<SepetYemekler>){
        list = list2
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

}