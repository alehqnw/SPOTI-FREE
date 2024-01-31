package com.example.proyectospotify.ui.pantalla

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.proyectospotify.ui.modelo.Rutas
import com.example.proyectospotify.ui.views.InicioViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Buscador(navController: NavController) {
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
            LazyColumn(){
                items(1){
                    ListaCanciones.forEachIndexed { indicador,cancion ->
                        if (cancion.Titulo.startsWith(searchText, ignoreCase = true)) {

                            DropdownMenuItem(
                                onClick = { indice(indicador,navController)},
                                text = { ListCard(Titulo = cancion.Titulo, Imagen =cancion.Imagen) }
                            )

                        }
                    }
                }
            }

        }
    }
}
fun indice (Indice:Int,navController: NavController){
    println("INDICE INDICADOR "+Indice)
    navController.navigate(Rutas.Cancion.ruta+"/${Indice}")

}
@Composable
fun ListCard(Titulo:String,Imagen:Int){
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)) {
        Row {
            Image(
                painter= painterResource(id = Imagen),
                contentDescription = null,
                modifier = Modifier.size(80.dp)
            )
            Column {
                Text(text = "Titulo: " +Titulo)
            }
        }
    }
}