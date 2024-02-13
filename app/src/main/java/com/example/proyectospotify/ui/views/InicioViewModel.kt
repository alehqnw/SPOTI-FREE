package com.example.proyectospotify.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
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

    @Composable
    fun CancionesCard(Titulo:String, Imagen:Int){
        Card(colors = CardDefaults.cardColors(Color.Red)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(id = Imagen),
                    contentDescription = null,
                    modifier = Modifier
                        .size(200.dp)
                )
                Text(text = Titulo,
                    modifier = Modifier
                        .fillMaxWidth())
            }
        }
    }
}