package com.example.proyectospotify.ui.pantalla

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.proyectospotify.ui.views.BBDD
import com.example.proyectospotify.ui.dataclass.Canciones
import com.example.proyectospotify.ui.dataclass.album
import com.example.proyectospotify.ui.modelo.Rutas
import com.example.proyectospotify.ui.views.SongListViewModel

@Composable
fun SongListView(navController:NavController, indice:Int) {
    var Canciones : MutableList<Canciones> = arrayListOf()
    val SongListViewModel:SongListViewModel = viewModel()
    var ListaAlbum : MutableList<Canciones> = mutableListOf()
    var esFavorito by remember{ mutableStateOf(false)}

    if(indice==0){
        Canciones=BBDD._canciones
        esFavorito=false
    }else{
        Canciones=BBDD.cancionesPersona
        esFavorito=true
    }
    ListaAlbum=Canciones
    println("Ultimo índice " +ListaAlbum.lastIndex+Canciones.lastIndex)

    Column(
        Modifier
            .fillMaxSize()
            .background(Color.Black)) {
        LazyColumn(){
            item{
                ListaAlbum.forEachIndexed { indicador, cancion ->
                    DropdownMenuItem(
                        onClick = { indice(indicador,navController, esFavorito)},
                        text = { SongListViewModel.CancionesCard(Titulo = cancion.Titulo, Imagen =cancion.Imagen) }
                    )
                }
            }
        }
    }
}