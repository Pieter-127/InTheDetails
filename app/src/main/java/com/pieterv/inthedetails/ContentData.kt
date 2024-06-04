package com.pieterv.inthedetails

data class ContentData(
    val index: Int? = null,
    val animalType: AnimalType? = null,
)

enum class AnimalType {
    Cat, Dog
}
