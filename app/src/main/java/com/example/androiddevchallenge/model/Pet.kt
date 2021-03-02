package com.example.androiddevchallenge.model

import com.example.androiddevchallenge.R

data class Pet(val name: String, val drawable: Int = R.drawable.david)

val pets = listOf(
    Pet("Daisy", R.drawable.david),
    Pet("Luna", R.drawable.fatty),
)
