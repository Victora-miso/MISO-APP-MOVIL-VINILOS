package com.alphazetakapp.misoappvinilos.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.alphazetakapp.misoappvinilos.R
import com.alphazetakapp.misoappvinilos.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.findNavController
import com.alphazetakapp.misoappvinilos.ui.artist.list.MusicianListFragment


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflar el archivo XML del menú
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        when (item.itemId) {
            R.id.albums -> {
                navController.navigate(R.id.fragment_album_list)
            }
            R.id.artists -> {
                // Iniciar la actividad MusicianListFragment
                //val intent = Intent(this, MusicianListFragment::class.java)
                //startActivity(intent)

                navController.navigate(R.id.fragment_musician_list) // Navega a MusicianListFragment

            }
            R.id.collector -> {
                navController.navigate(R.id.fragment_collector_list)
            }
            R.id.createAlbum -> {
                navController.navigate(R.id.fragment_album_create)
            }
        }
        return super.onOptionsItemSelected(item)
    }


}