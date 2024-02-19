package com.example.proyectospotify.ui.views


import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.proyectospotify.database.database
import com.example.proyectospotify.ui.dataclass.Dialogo
import com.example.proyectospotify.ui.modelo.Rutas
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


class sesionViewModel: ViewModel() {
    private val _usuario = MutableStateFlow("")
    val usuario = _usuario.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()
    private var bd = database

    val falsoEstrue = MutableStateFlow(false)
    fun cargarBD(){
        bd.leerUser()
        bd.leer()
    }
    fun actualizaUsuario(Nusuario:String){
        _usuario.value=Nusuario
    }
    fun actualizaPassword(Npassword: String){
        _password.value=Npassword
    }
    fun AnyadirCreeden(Nusuario:String,Npassword:String){
        bd.anyadirUser(Nusuario,Npassword)

    }
    fun compruebaCreeden(navController: NavController, auth: FirebaseAuth){
//        for (i in 0 until bd.listaUsers.value.size){
//            if((bd.listaUsers.value[i].usuario.equals(_usuario.value)) && (bd.listaUsers.value[i].password.equals(_password.value))){
//                navController.navigate(Rutas.Pantallas.ruta)
//                break
//            }else{
//                falsoEstrue.value=true
//
//            }
//
//        }
//        auth.signInWithCustomToken("539B8F6A-39CE-42A1-BEEC-CCBA687F4C4C").addOnCompleteListener{
//            task ->
//            if (task.isSuccessful) {
//                // Inicio de sesión exitoso, actualiza la interfaz de usuario con la información del usuario
//                val user = auth.currentUser
//                // ...
//            } else {
//                // Si el inicio de sesión falla, muestra un mensaje al usuario.
//                println("No se ha iniciado sesion correctamente")
//                // ...
//            }
//        }

        auth.signInWithEmailAndPassword(usuario.value,password.value).addOnCompleteListener{
                task ->
            if (task.isSuccessful) {
                // Inicio de sesión exitoso, actualiza la interfaz de usuario con la información del usuario
                val user = auth.currentUser
                navController.navigate(Rutas.Pantallas.ruta)
                // ...
            } else {
                // Si el inicio de sesión falla, muestra un mensaje al usuario.
                println("No se ha iniciado sesion correctamente")
                // ...
            }
        }
    }
}

