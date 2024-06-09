package com.example.reconocedorrazasapp.presentation.auth

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import com.example.reconocedorrazasapp.R

class LoginActivity : AppCompatActivity(), LogingFragment.LoginFragmentActions {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    override fun onRegisterButtonClick() {
         findNavController(R.id.nav_host_fragment)
             .navigate(LogingFragmentDirections.actionLogingFragmentToSignUpFragment())
    }
}