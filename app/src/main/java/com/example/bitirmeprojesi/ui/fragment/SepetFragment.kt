package com.example.bitirmeprojesi.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnticipateInterpolator
import android.view.animation.AnticipateOvershootInterpolator
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.data.entity.SepetYemekler
import com.example.bitirmeprojesi.data.entity.Yemekler
import com.example.bitirmeprojesi.databinding.FragmentAnaSayfaBinding
import com.example.bitirmeprojesi.databinding.FragmentDetayBinding
import com.example.bitirmeprojesi.databinding.FragmentSepetBinding
import com.example.bitirmeprojesi.ui.adapter.SepetAdapter
import com.example.bitirmeprojesi.ui.fragment.SepetFragmentDirections
import com.example.bitirmeprojesi.ui.viewmodel.SepetViewModel
import com.example.bitirmeprojesi.utils.gecisYap
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.getValue

@AndroidEntryPoint
class SepetFragment : Fragment() {



    private lateinit var binding : FragmentSepetBinding
    private val viewModel: SepetViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSepetBinding.inflate(inflater, container, false)

        binding.kedi.setAnimation("catSleeping.json")

        val rv = binding.sepetRV
        var sepetYemekListesi = listOf<SepetYemekler>()
        val adapter = SepetAdapter(sepetYemekListesi, requireContext(), viewModel)
        val linearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rv.adapter = adapter
        rv.layoutManager = linearLayoutManager

        val kedi = binding.kedi
        val bosYazisi = binding.sepetBos
        val duration = 1000

        val interpolator = AnticipateInterpolator()
        val interpolator2 = AnticipateOvershootInterpolator()
        val width = requireActivity().resources.displayMetrics.widthPixels

        viewModel.sepettekiYemekler.observe(viewLifecycleOwner){
            sepetYemekListesi = it
            adapter.listeyiGuncelle(sepetYemekListesi)

            var fiyat = 0
            for(yemek in sepetYemekListesi){
                fiyat += yemek.yemek_fiyat.toInt() * yemek.yemek_siparis_adet.toInt()
            }
            binding.fiyatToplam.text = fiyat.toString() + "₺"

            if(it.isNotEmpty() && !viewModel.animFlag){
                binding.satinAl.isEnabled = false

                kedi.animate().translationX(-width.toFloat()).setDuration(duration.toLong()).setInterpolator(interpolator2).withEndAction {
                    kedi.visibility = View.GONE
                }.start()

                bosYazisi.animate().translationX(-width.toFloat()).setDuration(duration.toLong()).setInterpolator(interpolator2).withEndAction {
                    bosYazisi.visibility = View.GONE
                }.start()

                rv.translationX = width.toFloat()
                rv.animate().translationX(0f).setDuration(duration.toLong()).setInterpolator(interpolator2).withEndAction {
                    binding.satinAl.isEnabled = true

                }.start()
                viewModel.animFlag = true
            }
            else if(it.isNotEmpty()){
                kedi.visibility = View.INVISIBLE
                bosYazisi.visibility = View.INVISIBLE
            }
            else{
                kedi.visibility = View.VISIBLE
                bosYazisi.visibility = View.VISIBLE
            }
        }



        binding.sepetGeri.setOnClickListener {
            val gecis = SepetFragmentDirections.sepetToAna()
            Navigation.gecisYap(it, gecis)
        }

        binding.satinAl.setOnClickListener {
            binding.satinAl.isEnabled = false

            if(sepetYemekListesi.isNotEmpty()) {
                sepetYemekListesi.forEach {
                    viewModel.sepettenYemekSil(it.sepet_yemek_id, "hross")
                }
                binding.sepetBos.visibility = View.INVISIBLE
                binding.sepetBos.translationX = width.toFloat()
                binding.sepetRV.visibility = View.GONE
                binding.kedi.translationX = 0f
                binding.kedi.setAnimation("verify.json")
                binding.kedi.progress = 0f
                binding.kedi.visibility = View.VISIBLE
                binding.kedi.playAnimation()

                lifecycleScope.launch {
                    delay(2000)
                    binding.sepetBos.visibility = View.VISIBLE
                    binding.kedi.cancelAnimation()
                    binding.kedi.animate().translationX(-width.toFloat())
                        .setDuration(1000).setInterpolator(interpolator2).withEndAction {
                        binding.kedi.translationX = width.toFloat()
                        binding.kedi.setAnimation("catSleeping.json")
                        binding.kedi.playAnimation()
                        binding.kedi.animate().translationX(0f).setDuration(1000)
                            .setInterpolator(interpolator2).start()
                        binding.sepetBos.animate().translationX(0f).setDuration(1000)
                            .setInterpolator(interpolator2).start()
                    }.start()
                }
            }
            lifecycleScope.launch {
                delay(2000)
                binding.satinAl.isEnabled = true

            }

        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.sepettekileriGetir("hross")
    }

}