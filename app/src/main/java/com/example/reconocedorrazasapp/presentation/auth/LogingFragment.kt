package com.example.reconocedorrazasapp.presentation.auth

import android.content.Context
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.reconocedorrazasapp.R
import com.example.reconocedorrazasapp.databinding.FragmentLoginBinding
import com.example.reconocedorrazasapp.utils.UtilsMethods

class LogingFragment : Fragment() {

    interface LoginFragmentActions{
        fun onRegisterButtonClick()
        fun onLoginFieldsValidate(email: String, password:String)
    }

    private  lateinit var loginFragmentAction: LoginFragmentActions
    private lateinit var binding : FragmentLoginBinding

    /**
     * Cuando el fragment se une al activity , el activity le pasa un contexto y puede
     * ocupar la interface en cualquier punto de la aplicación
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        loginFragmentAction = try {
            context as LoginFragmentActions
        } catch(e: ClassCastException){
            throw  ClassCastException("$context must implement LoginFragmentActions")
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater)
        binding.loginRegisterButton.setOnClickListener {
            loginFragmentAction.onRegisterButtonClick()
        }

        binding.loginRegisterButton.setOnClickListener {
            validateFields()
        }

        return binding.root
    }

    private fun validateFields() {
        binding.emailInput.error = ""
        binding.passwordInput.error = ""

        val email = binding.emailEdit.text.toString()

        if(!UtilsMethods.isValidEmail(email)){
            binding.emailInput.error = getString(R.string.mail_is_not_valid)
            return
        }

        val password = binding.passwordEdit.text.toString()
        if( password.isEmpty() ){
            binding.passwordInput.error = getString(R.string.password_is_not_empty)
            return
        }



        loginFragmentAction.onLoginFieldsValidate(email, password)
    }


}