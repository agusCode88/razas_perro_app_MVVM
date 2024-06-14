package com.example.reconocedorrazasapp.presentation.auth

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.reconocedorrazasapp.R
import com.example.reconocedorrazasapp.databinding.FragmentLoginBinding

class LogingFragment : Fragment() {

    interface LoginFragmentActions{
        fun onRegisterButtonClick()
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
        return binding.root
    }

}