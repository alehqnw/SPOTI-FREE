package com.example.proyectospotify.ui.pantalla

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyectospotify.ui.views.InicioViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Buscador() {
    var searchText by remember{ mutableStateOf("")}
    var estaActivoSB by remember{ mutableStateOf(false) }//Variable del SearchBar
    val ViewModel:InicioViewModel = viewModel()
    LaunchedEffect(key1 = Unit){
        ViewModel.CargaCanciones()
    }


    val ListaCanciones = ViewModel.canciones.value
    Column (modifier = Modifier.fillMaxWidth()){
        SearchBar(
            query = searchText,
            onQueryChange = {
                searchText = it
            },
            onSearch = {
            },
            active = estaActivoSB,
            onActiveChange = { estaActivoSB = !estaActivoSB },
            modifier = Modifier.fillMaxWidth()
        ) {
            ListaCanciones.forEach { cancion ->
                if (cancion.Titulo.startsWith(searchText, ignoreCase = true)) {
                    DropdownMenuItem(
                        onClick = {},
                        text = { Text(text = cancion.Titulo) }
                    )
                }
            }
        }
    }
}