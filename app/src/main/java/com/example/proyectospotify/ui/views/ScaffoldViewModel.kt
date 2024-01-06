package com.example.proyectospotify.ui.views

import androidx.lifecycle.ViewModel
import com.example.proyectospotify.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ScaffoldViewModel : ViewModel() {


    private val _titulo = MutableStateFlow("Player")
    val titulo = _titulo.asStateFlow()


    // Función que actualiza el título del Top bar
    fun modificarTitulo(nuevoTitulo : String){
        _titulo.value = nuevoTitulo
    }


    private val _cancionActual = MutableStateFlow(R.drawable.ggst)
    val cancionActual = _cancionActual.asStateFlow()


    // Función que actualiza la canción actual
    fun modificarCancion(){
        if(_cancionActual.value == R.drawable.ggst) _cancionActual.value = R.drawable.ggst
        else _cancionActual.value = R.drawable.ggst
    }
}