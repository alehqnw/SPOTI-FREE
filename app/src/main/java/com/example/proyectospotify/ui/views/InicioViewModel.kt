package com.example.proyectospotify.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.databinding.ObservableList
import androidx.lifecycle.ViewModel
import com.example.proyectospotify.R
import com.example.proyectospotify.ui.dataclass.Canciones
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class InicioViewModel: ViewModel() {
    private var _nombre = MutableStateFlow("Alejandro")
    val nombre = _nombre.asStateFlow()
    private val _canciones= MutableStateFlow(arrayListOf<Canciones>())
    val canciones = _canciones.asStateFlow()
    fun actualizarNombre(nuevoNombre: String){
        _nombre.value = nuevoNombre
    }
    fun CargaCanciones(){
        _canciones.value.add(Canciones("Extras","ROCK", R.raw.extras,R.drawable.ggst))
        _canciones.value.add(Canciones("Hellfire","ROCK", R.raw.hellfire,R.drawable.ggst))
    }

    @Composable
    fun CancionesCard(Titulo:String, Imagen:Int){
        Card(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            ,colors = CardDefaults.cardColors(Color.Red)
        ) {
            Row {
                Image(
                    painter = painterResource(id = Imagen),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(horizontal = 0.dp, vertical = 0.dp)
                        .size(100.dp)
                )
                //Text(text = Titulo, color = Color.White)

            }
        }
    }
}