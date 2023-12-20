package com.example.proyectospotify.ui.navegacion

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import com.example.proyectospotify.ui.modelo.Rutas
import com.example.proyectospotify.ui.pantalla.InicioView
import com.example.proyectospotify.ui.views.InicioViewModel

@Composable
fun GrafoNavegacion() {
    val navController = rememberNavController()
      NavHost(navController = navController,startDestination = Rutas.Pantallas.ruta){
        composable(Rutas.Pantallas.ruta){
            InicioView(navController=navController)
        }
      }



}