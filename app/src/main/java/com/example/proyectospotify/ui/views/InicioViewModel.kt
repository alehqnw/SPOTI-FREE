package com.example.proyectospotify.ui.views

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.example.proyectospotify.R
import com.example.proyectospotify.ui.dataclass.Canciones
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class InicioViewModel: ViewModel() {
    private var _nombre = MutableStateFlow("Alejandro")
    val nombre = _nombre.asStateFlow()
    private val _canciones:ArrayList<Canciones> = ArrayList<Canciones>()
    val canciones:ArrayList<Canciones> = _canciones.toMutableList() as ArrayList<Canciones>
    fun actualizarNombre(nuevoNombre: String){
        _nombre.value = nuevoNombre
    }
    fun CargaCanciones(){
        _canciones.add(Canciones("Extras","ROCK", R.raw.extras,R.drawable.ggst))
        _canciones.add(Canciones("Hellfire","ROCK", R.raw.hellfire,R.drawable.ggst))
    }
    fun RecogeCanciones():ArrayList<Canciones>{
        return canciones
    }
}