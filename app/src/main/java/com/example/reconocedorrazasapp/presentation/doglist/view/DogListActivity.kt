package com.example.reconocedorrazasapp.presentation.doglist.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.reconocedorrazasapp.api.ApiResponseStatus
import com.example.reconocedorrazasapp.databinding.ActivityDogListBinding
import com.example.reconocedorrazasapp.presentation.dogdetail.DogDetailActivity
import com.example.reconocedorrazasapp.presentation.dogdetail.DogDetailActivity.Companion.DOG_KEY
import com.example.reconocedorrazasapp.presentation.doglist.viewmodel.DogListViewModel
import com.example.reconocedorrazasapp.presentation.doglist.viewmodel.DogListViewModelFactory
import com.example.reconocedorrazasapp.repository.DogRepository

class DogListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDogListBinding
    private lateinit var viewModel: DogListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDogListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dogRepository = DogRepository()
        val viewModelFactory = DogListViewModelFactory(dogRepository)

        viewModel = ViewModelProvider(this,viewModelFactory).get(DogListViewModel::class.java)

        val recycler =  binding.recyclerDogs
        recycler.layoutManager = LinearLayoutManager(this)

        val loadingPB = binding.progressBar

        val dogAdapter = DogAdapter()

        dogAdapter.setOnItemClickListener {
            //Pasar el dog a DoDetailActivity
            val intent = Intent(this, DogDetailActivity::class.java)
            intent.putExtra(DOG_KEY, it)
            startActivity(intent)
        }

        recycler.adapter = dogAdapter

        viewModel.dogListLV.observe(this){
            dogAdapter.submitList(it)
        }

        viewModel.status.observe(this){

            when(it){
                ApiResponseStatus.LOADING -> {
                    // mostrar Progressbar
                    loadingPB.visibility = View.VISIBLE
                 }
                ApiResponseStatus.ERROR -> {
                    //Ocultar el Progres Bar
                    loadingPB.visibility = View.GONE
                    Toast.makeText(this,"Error al cargar los datos", Toast.LENGTH_SHORT).show()
                }

                ApiResponseStatus.SUCCESS -> {
                    // Oculatar el ProgressBar
                    loadingPB.visibility = View.GONE
                }

                else -> {
                    loadingPB.visibility = View.GONE
                    Toast.makeText(this,"Error desconocido", Toast.LENGTH_SHORT).show()
                }

            }
        }

    }
}