package com.example.proyectospotify.ui.pantalla

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.proyectospotify.R
import com.example.proyectospotify.ui.modelo.Rutas
import com.example.proyectospotify.ui.views.InicioViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun InicioView(navController: NavController){
    Column(modifier = Modifier
        .fillMaxSize()) {
        val ViewModel:InicioViewModel = viewModel()
        var Indice:Int = 0
        LazyColumn(modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally)){
            item{
                Spacer(modifier = Modifier.height(16.dp))
                IconButton(onClick = {Indice=0; navController.navigate(Rutas.ListaCanciones.ruta+"/${Indice}") },
                    modifier = Modifier.size(200.dp)
                    ,colors = IconButtonDefaults.iconButtonColors(Color.Transparent)) {
                    ViewModel.CancionesCard(Titulo = "Lista de Música", Imagen = R.drawable.spotifree)
                }
                Spacer(modifier = Modifier.height(16.dp))
                IconButton(onClick = {Indice=1; navController.navigate(Rutas.ListaCanciones.ruta+"/${Indice}") },
                    modifier = Modifier.size(200.dp)
                    ,colors = IconButtonDefaults.iconButtonColors(Color.Transparent)) {
                    ViewModel.CancionesCard(Titulo = "FAVORITOS", Imagen = R.drawable.favourite)
                }
            }
        }
    }
}

