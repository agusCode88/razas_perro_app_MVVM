package com.example.reconocedorrazasapp.utils

import android.util.Patterns

class UtilsMethods {
    companion object {
        fun isValidEmail(email: String): Boolean {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
    }
}