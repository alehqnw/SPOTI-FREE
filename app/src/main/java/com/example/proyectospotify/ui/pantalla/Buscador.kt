package com.example.proyectospotify.ui.pantalla

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
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
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.proyectospotify.R
import com.example.proyectospotify.database.database
import com.example.proyectospotify.ui.modelo.Rutas
import com.example.proyectospotify.ui.views.BBDD
import com.example.proyectospotify.ui.views.InicioViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Buscador(navController: NavController) {
    var searchText by remember{ mutableStateOf("")}
    var estaActivoSB by remember{ mutableStateOf(false) }//Variable del SearchBar
    val ViewModel:InicioViewModel = viewModel()


    val ListaCanciones = database.listaCanciones.collectAsState().value
    Column (modifier = Modifier
        .fillMaxWidth()){
        SearchBar(
            query = searchText,
            onQueryChange = {
                searchText = it
            },
            onSearch = {
            },
            active = estaActivoSB,
            onActiveChange = { estaActivoSB = !estaActivoSB },
            modifier = Modifier.fillMaxWidth(),
            colors = SearchBarDefaults.colors(androidx.compose.ui.graphics.Color.Black)
        ) {
            LazyColumn(){
                items(1){
                    ListaCanciones.forEachIndexed { indicador,cancion ->
                        if (cancion.Titulo.startsWith(searchText, ignoreCase = true)) {

                            DropdownMenuItem(
                                onClick = { indice(indicador,navController,false)},
                                text = { ListCard(Imagen = cancion.Imagen,Titulo = cancion.Titulo) }
                            )

                        }
                    }
                }
            }

        }
    }
}
fun indice (Indice:Int,navController: NavController,esFavorito:Boolean){
    println("INDICE INDICADOR "+Indice)
    navController.navigate(Rutas.Cancion.ruta+"/${Indice}/${esFavorito}")

}
@Composable
fun ListCard(Imagen:String,Titulo:String){
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)) {
        Row {
            val painter = rememberImagePainter(data = Imagen)
            Image(
                painter= painter,
                contentDescription = null,
                modifier = Modifier.size(80.dp)
            )
            Column {
                Text(text = "Titulo: " +Titulo)
            }
        }
    }
}