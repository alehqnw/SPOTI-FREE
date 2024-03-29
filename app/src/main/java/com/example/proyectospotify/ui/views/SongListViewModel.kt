package com.example.proyectospotify.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.proyectospotify.R
import com.example.proyectospotify.ui.modelo.Rutas
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SongListViewModel: ViewModel() {
    private val _titulo = MutableStateFlow("")
    val titulo = _titulo.asStateFlow()

    fun acutalizarTitulo(titulo:String){
        _titulo.value=titulo
    }

    fun indice (Indice:Int,navController: NavController){
        println("INDICE INDICADOR "+Indice)
        navController.navigate(Rutas.Cancion.ruta+"/${Indice}")

    }
    @Composable
    fun CancionesCard(Imagen:String,Titulo:String){
        Card(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)) {

            Row {
                val painter = rememberImagePainter(data = Imagen)
                Image(
                    painter= painter,
                    contentDescription = null,
                    modifier = Modifier.size(80.dp)
                )
                Column {
                    Text(text = "Titulo: " +Titulo)
                }
            }
        }
    }
}
