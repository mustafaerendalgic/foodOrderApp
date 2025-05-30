package com.example.bitirmeprojesi.ui.fragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.data.entity.SepetYemekler
import com.example.bitirmeprojesi.data.entity.Yemekler
import com.example.bitirmeprojesi.databinding.FragmentAnaSayfaBinding
import com.example.bitirmeprojesi.databinding.FragmentDetayBinding
import com.example.bitirmeprojesi.ui.adapter.DetayRVAdapter
import com.example.bitirmeprojesi.ui.adapter.SepetAdapter
import com.example.bitirmeprojesi.ui.adapter.YemekAdapter
import com.example.bitirmeprojesi.ui.itemdecoration.CustomItemDecoration
import com.example.bitirmeprojesi.ui.viewmodel.AnaSayfaViewModel
import com.example.bitirmeprojesi.ui.viewmodel.DetayViewModel
import com.example.bitirmeprojesi.ui.viewmodel.FavoriViewModel
import com.example.bitirmeprojesi.utils.gecisYap
import com.example.bitirmeprojesi.utils.icecekIsimleri
import com.example.bitirmeprojesi.utils.tatliIsimleri
import com.example.bitirmeprojesi.utils.yemekIsimleri
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.getValue

@AndroidEntryPoint
class DetayFragment : Fragment() {

    private lateinit var binding : FragmentDetayBinding
    private val viewModel : DetayViewModel by activityViewModels()
    private val viewModelFavori : FavoriViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.tumYemekleriGetir()
        viewModel.sepettekiYemekleriGetir()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val baseURL = "http://kasimadalan.pe.hu/yemekler/resimler/"

        binding = FragmentDetayBinding.inflate(inflater, container, false)
        binding.verify.visibility = View.INVISIBLE

        val bundle : DetayFragmentArgs by navArgs()
        val yemek = bundle.item
        var miktar = 1

        var yemekListesi = listOf<Yemekler>()

        val adapter = DetayRVAdapter(yemekListesi, requireContext(), viewModel)

        var tur : String

        if(yemekIsimleri.any {it.equals(yemek.yemek_adi)}){
            tur = ContextCompat.getString(requireContext(), R.string.anaYemek)
        }
        else if(tatliIsimleri.any { it.equals(yemek.yemek_adi) }){
            tur = ContextCompat.getString(requireContext(), R.string.tatli)
        }
        else{
            tur = ContextCompat.getString(requireContext(), R.string.icecek)
        }

        binding.yemekTuru.text = tur

        viewModel.yemekListesi.observe(viewLifecycleOwner) {
            when(tur){
                ContextCompat.getString(requireContext(), R.string.anaYemek) -> yemekListesi = it.filter { isim -> yemekIsimleri.none { it.equals(isim.yemek_adi) }}
                ContextCompat.getString(requireContext(), R.string.tatli) -> yemekListesi = it.filter { isim -> icecekIsimleri.any { it.equals(isim.yemek_adi) }}
                ContextCompat.getString(requireContext(), R.string.icecek) -> yemekListesi = it.filter { isim -> tatliIsimleri.any { it.equals(isim.yemek_adi) }}
                else -> yemekListesi = it.filter { isim -> tatliIsimleri.any { it.equals(isim.yemek_adi) }}
            }
            adapter.updateList(yemekListesi)
        }

        val rv = binding.yanindaIyiGiderRV
        rv.adapter = adapter
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rv.layoutManager = layoutManager
        rv.addItemDecoration(CustomItemDecoration(20))

        binding.yemekIsimDetay.text = yemek.yemek_adi
        Glide.with(requireContext()).load(baseURL + yemek.yemek_resim_adi).into(binding.yemekResimDetay)
        binding.itemFiyatDetay.text = yemek.yemek_fiyat.toString() + "₺"

        binding.miktar.text = miktar.toString()

        binding.detayButtonArtir.setOnClickListener {
            miktar++
            binding.miktar.text = miktar.toString()
        }

        binding.detayButtonAzalt.setOnClickListener {
            if(miktar != 1){
                miktar--
                binding.miktar.text = miktar.toString()
            }
        }

        binding.backbutton.setOnClickListener {
            val gecis = DetayFragmentDirections.detayToAna()
            Navigation.gecisYap(it, gecis)
        }

        binding.favorite.setOnClickListener {
            if(binding.favorite.progress == 0.0f){
                binding.favorite.speed = 1.0f
                binding.favorite.playAnimation()
            }
            else{
                binding.favorite.speed = -1.0f
                binding.favorite.playAnimation()
            }
        }

        lifecycleScope.launch{
            delay(700)
            binding.lottieAnimationView2.speed = 0.7f
            binding.lottieAnimationView2.playAnimation()
        }

        binding.lottieAnimationView2.setOnClickListener {
            binding.lottieAnimationView2.playAnimation()
        }

        binding.sepeteEkleDetay.setOnClickListener {
            binding.sepeteEkleDetay.isEnabled = false
            viewModel.sepeteEkleSartli(yemek, miktar)
            binding.sepeteEkleDetay.visibility = View.INVISIBLE
            binding.verify.visibility = View.VISIBLE
            binding.verify.playAnimation()
            lifecycleScope.launch {
                delay(2000)
                binding.sepeteEkleDetay.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.bar))
                binding.sepeteEkleDetay.alpha = 0f
                binding.sepeteEkleDetay.visibility = View.VISIBLE
                binding.sepeteEkleDetay.animate().alpha(1f).setDuration(500).start()
                binding.verify.visibility = View.INVISIBLE
            }
        }

        if(viewModelFavori.list[yemek.yemek_adi] != null) {
            binding.favorite.playAnimation()
        }

        binding.favorite.setOnClickListener {
            if(viewModelFavori.list[yemek.yemek_adi] == null && binding.favorite.progress == 0f) {
                binding.favorite.speed = 1.0f
                viewModelFavori.favorilereEkle(yemek, yemek.yemek_adi)
                binding.favorite.playAnimation()
            }
            else{
                viewModelFavori.favorilerdenSil(yemek.yemek_adi)
                binding.favorite.progress = 0f
                binding.favorite.cancelAnimation()
            }
        }

        return binding.root
    }



}