package com.example.proyectospotify.ui.modelo

sealed class Rutas(val ruta:String) {
    object InicioViewModel:Rutas("InicioViewModel")
}