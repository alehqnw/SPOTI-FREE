package com.example.proyectospotify.ui.pantalla

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.proyectospotify.database.database
import com.example.proyectospotify.ui.views.BBDD
import com.example.proyectospotify.ui.dataclass.Canciones
import com.example.proyectospotify.ui.dataclass.CancionesDB
import com.example.proyectospotify.ui.dataclass.album
import com.example.proyectospotify.ui.modelo.Rutas
import com.example.proyectospotify.ui.views.SongListViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun SongListView(navController:NavController, indice:Int) {
    var Canciones :SnapshotStateList<CancionesDB> = SnapshotStateList()
    val SongListViewModel:SongListViewModel = viewModel()
    var esFavorito by remember{ mutableStateOf(false)}

    if(indice==0){
        Canciones=database.listaCanciones.collectAsState().value
        esFavorito=false
    }else{
        //Canciones=database.listaCanciones.value.toMutableList()
        esFavorito=true
    }
    var ListaAlbum :SnapshotStateList<CancionesDB> = Canciones
    println("Ultimo índice " +ListaAlbum.lastIndex+Canciones.lastIndex)

    Column(
        Modifier
            .fillMaxSize()
            .background(Color.Black)) {
        LazyColumn(){
            item{
                ListaAlbum.forEachIndexed { indicador, cancion ->
                    DropdownMenuItem(
                        onClick = { indice(indicador,navController, esFavorito);database.borrar()},
                        text = { SongListViewModel.CancionesCard(Titulo = cancion.Titulo) }
                    )
                }
            }
        }
    }
}