package com.example.proyectospotify.ui.pantalla

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.proyectospotify.ui.dataclass.Canciones
import com.example.proyectospotify.ui.views.InicioViewModel

@Composable
fun InicioView(navController: NavController){
    Column(modifier = Modifier.fillMaxSize()) {
        val ViewModel:InicioViewModel = viewModel()
        ViewModel.CargaCanciones()
        val canciones:ArrayList<Canciones> = ViewModel.canciones.value
        LazyRow(modifier = Modifier
            .weight(2f)
            .fillMaxWidth()
            .background(Color.Black)){
            item{
                //Text(text = canciones[0].Titulo)
                //Meter canciones. de momento habrá un texto
                for (canciones1 in canciones) {
                    ViewModel.CancionesCard(Titulo = canciones1.Titulo, Imagen = canciones1.Imagen)
                }

            }
        }
    }
}

