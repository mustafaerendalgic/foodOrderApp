package com.example.bitirmeprojesi.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import android.view.animation.OvershootInterpolator
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.data.entity.Yemekler
import com.example.bitirmeprojesi.databinding.FragmentFavoriBinding
import com.example.bitirmeprojesi.ui.adapter.FavoriAdapter
import com.example.bitirmeprojesi.ui.viewmodel.FavoriViewModel
import com.example.bitirmeprojesi.utils.gecisYap


class FavoriFragment : Fragment() {

    private val viewModel: FavoriViewModel by activityViewModels()
    lateinit var binding: FragmentFavoriBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriBinding.inflate(inflater)

        val rv = binding.favorRV
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        var list = listOf<Yemekler>()
        val adapter = FavoriAdapter(list, requireContext(), viewModel)

        val width = requireActivity().resources.displayMetrics.widthPixels.toFloat()
        val interpolator = AnticipateOvershootInterpolator()

        viewModel.favoriList.observe(viewLifecycleOwner) {
            list = it
            adapter.updateList(list)
            val kedi = binding.kediFavori
            val yazi = binding.favoriBos
            val duration = 1000

            if(list.isEmpty()){

                kedi.translationX = width
                yazi.translationX = width
                kedi.visibility = View.VISIBLE
                yazi.visibility = View.VISIBLE
                rv.visibility = View.INVISIBLE
                kedi.animate().translationX(0f).setDuration(duration.toLong()).setInterpolator(interpolator).start()
                yazi.animate().translationX(0f).setDuration(duration.toLong()).setInterpolator(interpolator).start()

            }
            else if(viewModel.animFlag){

                rv.translationX = width
                rv.visibility = View.VISIBLE
                kedi.translationX = 0f
                yazi.translationX = 0f
                kedi.animate().translationX(-width).setDuration(duration.toLong()).setInterpolator(interpolator).start()
                yazi.animate().translationX(-width).setDuration(duration.toLong()).setInterpolator(interpolator).withEndAction {
                    kedi.visibility = View.GONE
                    yazi.visibility = View.GONE
                }.start()
                rv.animate().translationX(0f).setDuration(duration.toLong()).setInterpolator(interpolator).start()
                viewModel.animFlag = false
            }
            else if(list.isNotEmpty()){
                kedi.visibility = View.GONE
                yazi.visibility = View.GONE
                rv.visibility = View.VISIBLE
            }
        }

        rv.adapter = adapter
        rv.layoutManager = layoutManager



        binding.backButton.setOnClickListener {
            val gecis = FavoriFragmentDirections.favoriToAna()
            Navigation.gecisYap(it, gecis)
        }

        return binding.root
    }

}