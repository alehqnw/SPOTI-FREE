package com.example.proyectospotify.ui.modelo

sealed class Rutas(val ruta:String) {
    object Pantallas:Rutas("pantalla")
    object Cancion:Rutas("menuC")
    object Buscador:Rutas("buscador")
    object ListaCanciones:Rutas("listacanciones")
    object  InicioSesion:Rutas("iniciosesion")
}