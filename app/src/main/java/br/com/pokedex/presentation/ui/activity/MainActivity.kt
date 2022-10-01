package br.com.pokedex.presentation.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.pokedex.R
import br.com.pokedex.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}