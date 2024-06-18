package com.example.reconocedorrazasapp.data.api.dto

import com.example.reconocedorrazasapp.domain.model.Dog

class DogDTOMapper {
    private fun fromDogDtoToDogDomain(dogDTO: DogDTO): Dog {
        return Dog(
            dogDTO.id,
            dogDTO.index,
            dogDTO.name,
            dogDTO.type,
            dogDTO.heightFemale,
            dogDTO.heightMale,
            dogDTO.imageUrl,
            dogDTO.lifeExpectancy,
            dogDTO.temperament,
            dogDTO.weightFemale,
            dogDTO.weightMale
        )
    }

    fun fromDTOListToDogDomainList(dogDTOList: List<DogDTO>): List<Dog>{
        // Itera para cada elemento de la lista de DogDTO y aplica una transformación
        return dogDTOList.map { fromDogDtoToDogDomain(it) }
    }

}