package com.example.reconocedorrazasapp.presentation.doglist.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.reconocedorrazasapp.model.Dog
import com.example.reconocedorrazasapp.databinding.DogListItemBinding

class DogAdapter: ListAdapter<Dog, DogAdapter.DogViewwHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Dog>() {
        override fun areItemsTheSame(oldItem: Dog, newItem: Dog): Boolean {
            // Compara los ID o cualquier propiedad única que identifique al ítem
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Dog, newItem: Dog): Boolean {
            // Compara los contenidos de los ítems
            return oldItem == newItem
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewwHolder {

        val binding = DogListItemBinding.inflate(LayoutInflater.from(parent.context))
        return DogViewwHolder(binding)

    }

    override fun onBindViewHolder(holder: DogViewwHolder, position: Int) {

        val dog = getItem(position)
        holder.bindDog(dog)
    }

    inner class DogViewwHolder(val binding: DogListItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bindDog(dog: Dog){
            binding.txtDogName.text = dog.name
        }
    }
}