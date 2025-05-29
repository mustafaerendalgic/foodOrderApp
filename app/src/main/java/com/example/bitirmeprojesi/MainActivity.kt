package com.example.bitirmeprojesi

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.airbnb.lottie.LottieAnimationView
import com.example.bitirmeprojesi.ui.fragment.AnaSayfaFragmentDirections
import com.example.bitirmeprojesi.utils.gecisYap
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        val anaSayfa = findViewById<LottieAnimationView>(R.id.anaSayfaLottie)
        val sepet = findViewById<LottieAnimationView>(R.id.sepetLottie)
        val favori = findViewById<LottieAnimationView>(R.id.favoriLottie)

        val bottomNav = findViewById<LinearLayout>(R.id.navigation)

        anaSayfa.setOnClickListener {
            anaSayfa.playAnimation()
            if(navController.currentDestination?.id != R.id.anaSayfaFragment)
                navController.navigate(R.id.anaSayfaFragment)
        }

        sepet.setOnClickListener {
            sepet.speed = 0.5f
            sepet.playAnimation()
            if(navController.currentDestination?.id != R.id.sepetFragment)
                navController.navigate(R.id.sepetFragment)
        }

        favori.setOnClickListener {
            favori.playAnimation()
            if(navController.currentDestination?.id != R.id.sepetFragment)
                navController.navigate(R.id.sepetFragment)
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.detayFragment -> bottomNav.visibility = View.GONE
                else -> bottomNav.visibility = View.VISIBLE
            }
        }

        lifecycleScope.launch {
            delay(1000)
            sepet.speed = 0.5f
            sepet.playAnimation()
            anaSayfa.playAnimation()
            favori.playAnimation()
        }

    }

}