package com.example.quranapplication.data

data class Sura (
    val name:String,
    val number:Int,
    val source:Source
)

enum class Source{
    Makki,Madani
}