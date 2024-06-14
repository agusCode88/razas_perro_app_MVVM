package com.example.reconocedorrazasapp.presentation.auth

import android.content.Context
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.reconocedorrazasapp.R
import com.example.reconocedorrazasapp.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var signUpFragmentActions: SignUpFragmenActions

    interface SignUpFragmenActions {
        fun onSignUpFieldsValidate(email: String, password:String,
                                   passwordConfirmation: String )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        signUpFragmentActions = try {
            context as SignUpFragmenActions
        } catch(e: ClassCastException) {
            throw ClassCastException("$context must implement LoginFragmentActions")

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
        binding = FragmentSignUpBinding.inflate(inflater)
        setUpSignUpButton()
        return binding.root
    }

    private fun setUpSignUpButton() {
        binding.signUpButton.setOnClickListener {
            validateFields()
        }
    }

    private fun validateFields(){

        binding.emailInput.error = ""
        binding.passwordInput.error = ""
        binding.confirmPasswordInput.error = ""

        val email = binding.emailEdit.text.toString()

        if(!isValidEmail(email)){
            binding.emailInput.error = getString(R.string.mail_is_not_valid)
            return
        }

        val password = binding.passwordEdit.text.toString()
        if( password.isEmpty() ){
            binding.passwordInput.error = getString(R.string.password_is_not_empty)
            return
        }

        val passwordConfirmation = binding.confirmPasswordEdit.text.toString()
        if(passwordConfirmation.isEmpty() ){
           binding.confirmPasswordInput.error = getString(R.string.confirm_password_is_not_empty)
           return
        }

        if (password != passwordConfirmation){
            binding.passwordInput.error = getString(R.string.passwords_do_not_match)
            return
        }

        signUpFragmentActions.onSignUpFieldsValidate(email, password, passwordConfirmation)

    }

    private fun isValidEmail(email: String?): Boolean{
        return !email.isNullOrEmpty() &&
                Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

}