package com.example.reconocedorrazasapp.presentation.settings

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.reconocedorrazasapp.R
import com.example.reconocedorrazasapp.databinding.ActivitySettingsBinding
import com.example.reconocedorrazasapp.domain.model.User
import com.example.reconocedorrazasapp.presentation.auth.LoginActivity

class SettingsActivity : AppCompatActivity() {

    private lateinit var bindingSettings : ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        bindingSettings = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(bindingSettings.root)

        bindingSettings.logOutBtn.setOnClickListener {
            logOut()
        }

    }

    private fun logOut() {
       User.logOut(this)
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}