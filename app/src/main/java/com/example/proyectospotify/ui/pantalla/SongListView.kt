package com.example.proyectospotify.ui.pantalla

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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


    if(indice==0){
        Canciones=BBDD._canciones
    }else{
        Canciones=BBDD.cancionesPersona
    }

    println("Ultimo índice " +ListaAlbum.lastIndex+Canciones.lastIndex)
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.Black)) {
        LazyColumn(){
            item{
                Canciones.forEachIndexed { indicador, cancion ->
                    println(cancion)
                    DropdownMenuItem(
                        onClick = { indice(indicador,navController)},
                        text = { SongListViewModel.CancionesCard(Titulo = cancion.Titulo, Imagen =cancion.Imagen) }
                    )
                }
            }
        }
    }
}