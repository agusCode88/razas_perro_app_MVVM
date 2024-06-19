package com.example.reconocedorrazasapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.reconocedorrazasapp.databinding.ActivityMainBinding
import com.example.reconocedorrazasapp.domain.model.User
import com.example.reconocedorrazasapp.presentation.auth.LoginActivity
import com.example.reconocedorrazasapp.presentation.settings.SettingsActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = User.getLoggedInUser(this)
        if (user == null){
            openLoginActivity()
            return
        }

        binding.settingsAppFab.setOnClickListener {
            openSettingsActivity()
        }

    }

    private fun openSettingsActivity() {
        startActivity(Intent(this , SettingsActivity::class.java))
    }

    private fun openLoginActivity(){
        startActivity(Intent(this,LoginActivity::class.java))
        finish()
    }
}