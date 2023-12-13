package com.example.proyectospotify.ui.views

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class InicioViewModel: ViewModel() {
    private var _nombre = MutableStateFlow("Alejandro")
    val nombre = _nombre.asStateFlow()

    fun actualizarNombre(nuevoNombre: String){
        _nombre.value = nuevoNombre
    }
}