package br.com.pokedex.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.pokedex.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}