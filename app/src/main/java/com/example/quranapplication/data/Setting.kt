package com.example.quranapplication.data

data class Setting(
    val settingName:Int,
    val imageSource:Int,
    val action:Action
)

enum class Action{
    EDIT_SIZE,LANGUAGE_SWITCH
}
