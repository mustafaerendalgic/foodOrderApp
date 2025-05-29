package com.example.bitirmeprojesi.ui.fragment

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.GridLayoutManager
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.data.entity.Yemekler
import com.example.bitirmeprojesi.databinding.FragmentAnaSayfaBinding
import com.example.bitirmeprojesi.ui.adapter.YemekAdapter
import com.example.bitirmeprojesi.ui.itemdecoration.CustomItemDecoration
import com.example.bitirmeprojesi.ui.viewmodel.AnaSayfaViewModel
import com.example.bitirmeprojesi.ui.viewmodel.FavoriViewModel
import com.example.bitirmeprojesi.utils.ColorAnimationForBackground

import com.example.bitirmeprojesi.utils.ColorAnimationForBar
import com.example.bitirmeprojesi.utils.ListeFiltrele
import com.example.bitirmeprojesi.utils.gecisYap
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnaSayfaFragment : Fragment() {

    private lateinit var binding : FragmentAnaSayfaBinding
    private val viewModel: AnaSayfaViewModel by activityViewModels()
    private val viewModelFavori: FavoriViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAnaSayfaBinding.inflate(inflater, container, false)

        Glide.with(requireContext()).load(R.drawable.tumuyenisvg).into(binding.tumu)
        Glide.with(requireContext()).load(R.drawable.anayemekyeni).into(binding.anaYemek)
        Glide.with(requireContext()).load(R.drawable.tatliyenisvg).into(binding.tatli)
        Glide.with(requireContext()).load(R.drawable.icecekyenisvg).into(binding.icecek)

        var yemekList = listOf<Yemekler>()

        val yemekAdapter = YemekAdapter(yemekList, requireContext(), viewModelFavori)

        val yemekIsmi = ContextCompat.getString(requireContext(), R.string.anaYemek)
        val tumuIsmi = ContextCompat.getString(requireContext(), R.string.tumu)
        val tatliIsmi = ContextCompat.getString(requireContext(), R.string.tatli)
        val icecekIsmi = ContextCompat.getString(requireContext(), R.string.icecek)

        var tur: String = tumuIsmi

        viewModel.yemekListesi.observe(viewLifecycleOwner) {
            viewModel.yemekListesi.observe(viewLifecycleOwner) {
                yemekList = it
                yemekAdapter.updateList(yemekList)
            }
        }

        binding.anaRecycler.adapter = yemekAdapter
        val gridLayoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        binding.anaRecycler.layoutManager = gridLayoutManager

        val density = resources.displayMetrics.density
        val width = resources.displayMetrics.widthPixels
        val itemWidth = (170*density)
        val space = (width - 2*itemWidth) / 3

        binding.anaRecycler.addItemDecoration(CustomItemDecoration(space.toInt()))

        var colorFrom = ContextCompat.getColor(requireContext(), R.color.tema)
        var colorTo = ContextCompat.getColor(requireContext(), R.color.tema)

        binding.icecekKart.setOnClickListener {
            colorTo = ContextCompat.getColor(requireContext(), R.color.icecek)
            ColorAnimationForBar(colorFrom, colorTo, binding.barRenk)
            yemekAdapter.updateColors(colorFrom, colorTo)
            ColorAnimationForBackground(binding.imageView, colorFrom, colorTo)
            colorFrom = ContextCompat.getColor(requireContext(), R.color.icecek)
            tur = icecekIsmi
            ListeFiltrele(tur, yemekAdapter, requireContext(), yemekList)
        }

        binding.tatliKart.setOnClickListener {
            colorTo = ContextCompat.getColor(requireContext(), R.color.tatli)
            ColorAnimationForBar(colorFrom, colorTo, binding.barRenk)
            yemekAdapter.updateColors(colorFrom, colorTo)
            ColorAnimationForBackground(binding.imageView, colorFrom, colorTo)
            colorFrom = ContextCompat.getColor(requireContext(), R.color.tatli)
            tur = tatliIsmi
            ListeFiltrele(tur, yemekAdapter, requireContext(), yemekList)
        }

        binding.tumuKart.setOnClickListener {
            colorTo = ContextCompat.getColor(requireContext(), R.color.tema)
            ColorAnimationForBar(colorFrom, colorTo, binding.barRenk)
            yemekAdapter.updateColors(colorFrom, colorTo)
            ColorAnimationForBackground(binding.imageView, colorFrom, colorTo)
            colorFrom = ContextCompat.getColor(requireContext(), R.color.tema)
            tur = tumuIsmi
            ListeFiltrele(tur, yemekAdapter, requireContext(), yemekList)
        }

        binding.anaYemekKart.setOnClickListener {
            colorTo = ContextCompat.getColor(requireContext(), R.color.anaYemek)
            ColorAnimationForBar(colorFrom, colorTo, binding.barRenk)
            yemekAdapter.updateColors(colorFrom, colorTo)
            ColorAnimationForBackground(binding.imageView, colorFrom, colorTo)
            colorFrom = ContextCompat.getColor(requireContext(), R.color.anaYemek)
            tur = yemekIsmi
            ListeFiltrele(tur, yemekAdapter, requireContext(), yemekList)
        }

        binding.aramaCubuguYazi.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(
                p0: CharSequence?,
                p1: Int,
                p2: Int,
                p3: Int
            ) {

            }

            override fun onTextChanged(
                p0: CharSequence?,
                p1: Int,
                p2: Int,
                p3: Int
            ) {

            }

            override fun afterTextChanged(p0: Editable?) {
                val text = p0.toString()
                val list = yemekList.filter { it.yemek_adi.uppercase().contains(text.uppercase()) }
                yemekAdapter.updateList(list)
            }

        })


        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.tumYemekleriGetir()
    }



}