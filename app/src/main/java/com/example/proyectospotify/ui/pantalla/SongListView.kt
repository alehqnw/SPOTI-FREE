package com.example.proyectospotify.ui.pantalla

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.proyectospotify.ui.views.BBDD

import com.example.proyectospotify.ui.dataclass.Canciones
import com.example.proyectospotify.ui.dataclass.album
import com.example.proyectospotify.ui.views.SongListViewModel

@Composable
fun SongListView(navController:NavController, indice:Int) {
    val bdCanciones: BBDD = viewModel()
    var Canciones : MutableList<Canciones> = arrayListOf()
    val SongListViewModel:SongListViewModel = viewModel()
    var ListaAlbum: MutableList<album> = arrayListOf()
    LaunchedEffect(key1 = Unit){
        bdCanciones.CargaCanciones()
    }
    Canciones = (bdCanciones._canciones)
    ListaAlbum = arrayListOf(album(Canciones))
    println("Ultimo índice " +ListaAlbum.lastIndex+Canciones.lastIndex)
    Column(Modifier.fillMaxSize()) {
        LazyColumn(){
            item{
                ListaAlbum[indice].Album.forEachIndexed { indicador,cancion ->
                    DropdownMenuItem(
                        onClick = { indice(indicador,navController)},
                        text = { SongListViewModel.CancionesCard(Titulo = cancion.Titulo, Imagen =cancion.Imagen) }
                    )
                }
            }
        }
    }

}