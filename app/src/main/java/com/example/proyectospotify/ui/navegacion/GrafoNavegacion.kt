package com.example.proyectospotify.ui.navegacion

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.proyectospotify.ui.modelo.Rutas
import com.example.proyectospotify.ui.pantalla.BarraInferior
import com.example.proyectospotify.ui.pantalla.BarraSuperior
import com.example.proyectospotify.ui.pantalla.InicioView
import com.example.proyectospotify.ui.views.InicioViewModel
import com.example.proyectospotify.ui.views.ScaffoldViewModel

@Composable
fun GrafoNavegacion() {
    val navController = rememberNavController()
    val entradaNavActual by navController.currentBackStackEntryAsState()
    val viewModelScaffold : ScaffoldViewModel = viewModel()
    val rutaActual = entradaNavActual?.destination?.route
    Scaffold(topBar = { BarraSuperior(titulo = if (rutaActual == Rutas.Pantallas.ruta) "REPRODUCIR" else "FOTO") },
        bottomBar = { BarraInferior(funcionNavegarPlayer = {
            // solo puedo ir pa atras si estoy en...
            navController.popBackStack()
        }
            , funcionNavegarFoto = {
                navController.navigate(Rutas.Pantallas.ruta)
            })},
        content = {
            // paddingValues representa los dp que hay para evitar que el contenido se solape con las barras
                paddingValues ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                color = MaterialTheme.colorScheme.background
            ) {

                NavHost(navController = navController,startDestination = Rutas.Pantallas.ruta){
                    composable(Rutas.Pantallas.ruta){
                        InicioView(navController=navController)
                    }
                }

            }

        }
    )
}
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun preview(){
    GrafoNavegacion()
}