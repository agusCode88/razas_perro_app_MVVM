package com.example.reconocedorrazasapp.presentation.doglist.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.reconocedorrazasapp.R
import com.example.reconocedorrazasapp.databinding.DogListItemBinding
import com.example.reconocedorrazasapp.domain.model.Dog

class DogAdapter: ListAdapter<Dog, DogAdapter.DogViewwHolder>(DiffCallback) {

    private var onItemClickListener: ((Dog) ->Unit)? = null
    private var onLongItemClickListener: ((Dog) -> Unit)? = null

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

   fun setOnItemClickListener(onItemClickListener: (Dog) -> Unit){
       this.onItemClickListener = onItemClickListener
   }

    fun setOnLongItemClickListener(onLongItemClickListener: (Dog) -> Unit){
        this.onLongItemClickListener = onLongItemClickListener
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

            if(dog.inCollection){

                binding.dogListItemLayout.background = ContextCompat.getDrawable(
                    binding.dogImage.context,
                    R.drawable.dog_list_item_background
                )

                binding.dogImage.visibility = View.VISIBLE
                binding.dogIndex.visibility = View.GONE


                binding.dogListItemLayout.setOnClickListener {
                    onItemClickListener?.invoke(dog)
                }

                binding.dogImage.load(dog.imageUrl)
            } else {
                binding.dogImage.visibility = View.GONE
                binding.dogIndex.visibility = View.VISIBLE
                binding.dogIndex.text = dog.index.toString()
                binding.dogListItemLayout.background = ContextCompat.getDrawable(
                    binding.dogImage.context,
                    R.drawable.dog_list_item_null_background
                )

                binding.dogListItemLayout.setOnLongClickListener{
                    onLongItemClickListener?.invoke(dog)
                    true
                }
            }

        }
    }
}