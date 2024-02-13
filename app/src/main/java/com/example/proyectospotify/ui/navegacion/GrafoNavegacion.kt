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
import com.example.proyectospotify.ui.dataclass.Canciones
import com.example.proyectospotify.ui.modelo.Rutas
import com.example.proyectospotify.ui.pantalla.BarraInferior
import com.example.proyectospotify.ui.pantalla.BarraSuperior
import com.example.proyectospotify.ui.pantalla.Buscador
import com.example.proyectospotify.ui.pantalla.InicioView
import com.example.proyectospotify.ui.pantalla.SongListView
import com.example.proyectospotify.ui.pantalla.SongScreen
import com.example.proyectospotify.ui.pantalla.sesionView
import com.example.proyectospotify.ui.views.InicioViewModel
import com.example.proyectospotify.ui.views.ScaffoldViewModel

@Composable
fun GrafoNavegacion() {
    val navController = rememberNavController()
    val entradaNavActual by navController.currentBackStackEntryAsState()
    val rutaActual = entradaNavActual?.destination?.route
    Scaffold(topBar = {if(rutaActual!=Rutas.Cancion.ruta){
        if(rutaActual==Rutas.Buscador.ruta || rutaActual==Rutas.Pantallas.ruta){
            BarraSuperior(titulo =
            when(rutaActual){
                Rutas.Pantallas.ruta ->{
                    "PANTALLA DE INICIO"
                }
                Rutas.Buscador.ruta->{
                    "BUSCADOR"
                }
                else->{
                    ""
                }
            })
        }

    }

                      },
        bottomBar = { if(rutaActual==Rutas.Buscador.ruta || rutaActual==Rutas.Pantallas.ruta){
            BarraInferior(funcionNavegarPlayer = {
            // solo puedo ir pa atras si estoy en...
            navController.navigate(Rutas.Pantallas.ruta)
        }
            , funcionNavegarFoto = {
                navController.navigate(Rutas.Buscador.ruta)
            })}
                    },
        content = {
            // paddingValues representa los dp que hay para evitar que el contenido se solape con las barras
                paddingValues ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                color = MaterialTheme.colorScheme.background
            ) {

                NavHost(navController = navController,startDestination = Rutas.InicioSesion.ruta){
                    println(rutaActual)
                    composable(Rutas.InicioSesion.ruta){
                        sesionView(navController = navController)
                    }
                    composable(Rutas.Pantallas.ruta){
                        InicioView(navController=navController)
                    }
                    composable(Rutas.Cancion.ruta+"/{IndiceRecibido}"){
                        val Indice = it.arguments?.getString("IndiceRecibido")?.toInt()
                        SongScreen(navController,Indice)
                    }
                    composable(Rutas.Buscador.ruta){
                        Buscador(navController=navController)
                    }
                    composable(Rutas.ListaCanciones.ruta+"/{IndiceRecibido}"){
                        val indiceList = it.arguments?.getString("IndiceRecibido")?.toInt()
                        if (indiceList != null) {
                            SongListView(navController = navController,indice = indiceList)
                        }
                    }

                }

            }

        }
    )
}