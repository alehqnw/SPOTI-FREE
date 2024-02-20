package com.example.proyectospotify.ui.dataclass

data class User(
    var usuario:String="",
    var canciones:List<String> = listOf()
) {
    var idUser = ""
}