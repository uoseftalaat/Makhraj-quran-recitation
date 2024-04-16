package com.example.quranapplication.data

data class Sura (
    val name:String,
    val number:Int,
    val source:Source,
    val startingPage:Int
)

enum class Source{
    Makki,Madani
}