package com.example.proyectospotify.ui.pantalla

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.proyectospotify.R
import com.example.proyectospotify.ui.dataclass.Canciones
import com.example.proyectospotify.ui.modelo.Rutas
import com.example.proyectospotify.ui.views.InicioViewModel
import com.example.proyectospotify.ui.views.SongViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun InicioView(navController: NavController){
    Column(modifier = Modifier.fillMaxSize()) {
        val ViewModel:InicioViewModel = viewModel()
        ViewModel.CargaCanciones()
        val canciones:ArrayList<Canciones> = ViewModel.canciones.value
        Row(){
            IconButton(onClick = { navController.navigate(Rutas.Cancion.ruta) },
                modifier = Modifier.size(300.dp)
                ,colors = IconButtonDefaults.iconButtonColors(Color.Transparent)) {
                ViewModel.CancionesCard(Titulo = "", Imagen = R.drawable.spotifree)
            }

        }
    }
}

