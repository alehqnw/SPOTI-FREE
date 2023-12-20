package com.example.proyectospotify.ui.pantalla

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
        val canciones:ArrayList<Canciones> = ViewModel.RecogeCanciones().toMutableList() as ArrayList<Canciones>
        LazyRow(modifier = Modifier
            .weight(5f)
            .fillMaxWidth()){
            items(5){
                //Meter canciones. de momento habrá un texto
                Text(text = "Adiós Mundo")
            }
        }
    }
}