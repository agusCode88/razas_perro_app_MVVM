package com.example.reconocedorrazasapp.doglist.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.reconocedorrazasapp.databinding.ActivityDogListBinding
import com.example.reconocedorrazasapp.doglist.viewmodel.DogListViewModel
import com.example.reconocedorrazasapp.doglist.viewmodel.DogListViewModelFactory
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

        val dogAdapter = DogAdapter()
        recycler.adapter = dogAdapter

        viewModel.dogListLV.observe(this){
            dogAdapter.submitList(it)
        }

    }
}