package br.com.pokedex.presentation.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.pokedex.R
import br.com.pokedex.databinding.ActivityMainBinding
import br.com.pokedex.presentation.ui.fragment.MenuFragment
import com.google.android.material.appbar.MaterialToolbar

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}