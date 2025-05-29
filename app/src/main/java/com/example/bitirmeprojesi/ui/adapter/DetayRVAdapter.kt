package com.example.bitirmeprojesi.ui.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.data.entity.Yemekler
import com.example.bitirmeprojesi.ui.viewmodel.DetayViewModel
import com.example.bitirmeprojesi.ui.viewmodel.FavoriViewModel
import com.example.bitirmeprojesi.utils.ColorAnimationForBackground
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.w3c.dom.Text

class DetayVH(val item: View) : RecyclerView.ViewHolder(item){
    val resim: ImageView
    val isim: TextView
    val fiyat: TextView
    val buton: AppCompatButton
    val anim: LottieAnimationView
    init {
        resim = item.findViewById<ImageView>(R.id.detayitemResim)
        isim = item.findViewById<TextView>(R.id.detayitemIsim)
        fiyat = item.findViewById<TextView>(R.id.detaykartFiyat)
        buton = item.findViewById<AppCompatButton>(R.id.yanindaIyiGiderSepeteEkle)
        anim = item.findViewById<LottieAnimationView>(R.id.verify2)
    }
}

class DetayRVAdapter(var list: List<Yemekler>, val ctx: Context, val viewModel: DetayViewModel) : RecyclerView.Adapter<DetayVH>(){

    private val baseURL = "http://kasimadalan.pe.hu/yemekler/resimler/"

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DetayVH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.detay_kart_tasarim, parent, false)
        return DetayVH(v)
    }

    override fun onBindViewHolder(
        holder: DetayVH,
        position: Int
    ) {
        val item = list[position]
        holder.anim.visibility = View.INVISIBLE
        holder.isim.text = item.yemek_adi
        holder.fiyat.text = item.yemek_fiyat.toString() + "₺"
        Glide.with(ctx).load(baseURL + item.yemek_resim_adi).into(holder.resim)

        holder.buton.setOnClickListener {
            holder.buton.isEnabled = false
            viewModel.sepettekiYemekleriGetir()
            holder.buton.visibility = View.INVISIBLE
            holder.anim.visibility = View.VISIBLE
            holder.anim.playAnimation()

            val miktar = 1
            viewModel.sepeteEkleSartli(item, miktar)
            viewModel.viewModelScope.launch{
                delay(2000)
                holder.anim.visibility = View.INVISIBLE
                holder.buton.alpha = 0f
                holder.buton.visibility = View.VISIBLE
                holder.buton.animate().alpha(1f).setDuration(500).start()
            }

        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateList(list2: List<Yemekler>){
        list = list2
        notifyDataSetChanged()
    }

}