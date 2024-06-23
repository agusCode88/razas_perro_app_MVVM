package com.example.reconocedorrazasapp

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.reconocedorrazasapp.databinding.ActivityViewPhotoBinding
import java.io.File

class ViewPhotoActivity : AppCompatActivity() {

    private lateinit var binding : ActivityViewPhotoBinding
    companion object{
       const val PHOTO_URL_KEY = "photo_url"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityViewPhotoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val photoUri = intent.extras?.getString(PHOTO_URL_KEY)
        val uri = Uri.parse(photoUri)

        if(uri == null){
            Toast.makeText(this,
                "Erro showing image no photo uri",
                Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        binding.photoImage.load(uri.path?.let { File(it) })

    }
}