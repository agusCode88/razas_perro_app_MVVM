package com.example.reconocedorrazasapp.presentation.dogdetail

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import coil.load
import com.example.reconocedorrazasapp.R
import com.example.reconocedorrazasapp.databinding.ActivityDogDetailBinding
import com.example.reconocedorrazasapp.model.Dog

class DogDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDogDetailBinding

    companion object{
        const val DOG_KEY = "dog"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDogDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dog = intent?.extras?.getParcelable<Dog>(DOG_KEY)

        if( dog == null ){
            Toast.makeText(this,"Dog not found", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        binding.dogNameText.text = dog.name
        binding.dogIndex.text = getString(R.string.dog_index_format, dog.index)
        binding.lifeExpectancy.text = getString(R.string.dog_life_expectancy_format, dog.lifeExpectancy)
        binding.dogImage.load(dog.imageUrl)
        binding.closeButton.setOnClickListener {
            finish()
        }
    }
}