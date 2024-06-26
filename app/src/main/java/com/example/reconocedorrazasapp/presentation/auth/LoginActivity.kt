package com.example.reconocedorrazasapp.presentation.auth

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.reconocedorrazasapp.MainActivity
import com.example.reconocedorrazasapp.R
import com.example.reconocedorrazasapp.data.api.connection.ApiResponseStatus
import com.example.reconocedorrazasapp.databinding.ActivityLoginBinding
import com.example.reconocedorrazasapp.domain.model.User
import com.example.reconocedorrazasapp.presentation.auth.viewmodel.AuthViewModel

class LoginActivity : AppCompatActivity(), LogingFragment.LoginFragmentActions, SignUpFragment.SignUpFragmenActions {

    private val viewModel: AuthViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.status.observe(this){
            when(it){
                is ApiResponseStatus.Error -> {
                    binding.loadingWheel.visibility = View.GONE
                    showAlertDialog(it.errorMessageId)
                }
                is ApiResponseStatus.Loading -> binding.loadingWheel.visibility = View.VISIBLE
                is ApiResponseStatus.Success -> binding.loadingWheel.visibility = View.GONE
            }
        }

        viewModel.user.observe(this){
            if(it != null) {
                User.setLoggedInUser(this, it)
                startMainActivity()
            }

        }

    }

    private fun startMainActivity() {
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }

    override fun onRegisterButtonClick() {
         findNavController(R.id.nav_host_fragment)
             .navigate(LogingFragmentDirections.actionLogingFragmentToSignUpFragment())
    }

    private fun showAlertDialog(messageId: Int){
        AlertDialog.Builder(this)
            .setTitle(R.string.error_sign_in)
            .setMessage(messageId)
            .setPositiveButton(android.R.string.ok){_, _ -> /** Dismiss Dialog */}
            .create()
            .show()
    }

    override fun onLoginFieldsValidate(email: String, password: String) {
        viewModel.login(email,password)
    }

    override fun onSignUpFieldsValidate(
        email: String,
        password: String,
        passwordConfirmation: String
    ) {
        // Acá tengo que implementar el MVVM para el SigUp
        viewModel.signUp(email,password,passwordConfirmation)
    }
}