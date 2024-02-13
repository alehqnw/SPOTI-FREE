package com.example.proyectospotify.ui.views


import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.proyectospotify.ui.dataclass.Dialogo
import com.example.proyectospotify.ui.modelo.Rutas
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


class sesionViewModel: ViewModel() {
    private val _usuario = MutableStateFlow("")
    val usuario = _usuario.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()


    val falsoEstrue = MutableStateFlow(false)
    fun cargarBD(){
        BBDD.CargaUsers()
    }
    fun actualizaUsuario(Nusuario:String){
        _usuario.value=Nusuario
    }
    fun actualizaPassword(Npassword: String){
        _password.value=Npassword
    }
    fun AnyadirCreeden(Nusuario:String,Npassword:String){
        BBDD.AnyadirUsers(Nusuario)
        BBDD.AnyadirPassword(Npassword)
    }
    fun compruebaCreeden(navController: NavController){
        for (i in 0 until BBDD.listaUsers.size){
            println(_usuario.value + _password.value)
            if((BBDD.listaUsers[i].equals(_usuario.value)) && (BBDD.listaPassowrd[i].equals(_password.value))){
                navController.navigate(Rutas.Pantallas.ruta)
                break
            }else{
                falsoEstrue.value=true
                println("Es falso "+BBDD.listaUsers[i] + " passsword " +BBDD.listaPassowrd[i])
            }

        }
    }
}