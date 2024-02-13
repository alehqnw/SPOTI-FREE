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


    private val _basedatos = BBDD()
    val falsoEstrue = MutableStateFlow(false)
    fun cargarBD(){
        _basedatos.CargaUsers()
    }
    fun actualizaUsuario(Nusuario:String,Npassword:String){
        _usuario.value=Nusuario
        _password.value=Npassword
    }
    fun AnyadirCreeden(Nusuario:String,Npassword:String){
        _basedatos.AnyadirUsers(Nusuario)
        _basedatos.AnyadirPassword(Npassword)
    }
    fun compruebaCreeden(navController: NavController){
        val tamanyo = _basedatos._canciones.size
        _basedatos.listaUsers.forEachIndexed(){indice,it->
            println(_usuario.value + _password.value)
            if(_basedatos.listaUsers[indice] == _usuario.value && _basedatos.listaPassowrd[indice] == _password.value){
                println("USUARIO "+_usuario.value + "contrasña "+_password.value)
                println("bd" +_basedatos.listaUsers[indice]+_basedatos.listaPassowrd[indice])
                navController.navigate(Rutas.Pantallas.ruta)
            }else{
                falsoEstrue.value=true
            }

        }
    }
}