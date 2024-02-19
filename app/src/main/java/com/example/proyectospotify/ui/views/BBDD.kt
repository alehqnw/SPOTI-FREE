package com.example.proyectospotify.ui.views


import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.example.proyectospotify.R
import com.example.proyectospotify.database.database
import com.example.proyectospotify.ui.dataclass.Canciones
class BBDD {
    val _canciones : MutableList<Canciones> = mutableListOf()
    val listaUsers : MutableList<String> = mutableListOf()
    val listaPassowrd : MutableList<String> = mutableListOf()
    val cancionesPersona : MutableList<Canciones> = mutableListOf()
    private var logeado: Int? =0
    fun CargaUsers(){
        listaUsers.add("Alejandro")
        listaUsers.add("Alverto")
        listaUsers.add("Juan")
        listaUsers.add("admin")
        listaPassowrd.add("1234")//Contraseña de Alejandro y las demás con tal
        listaPassowrd.add("12345")
        listaPassowrd.add("123456")
        listaPassowrd.add("admin")
    }
    fun getPersona():MutableList<Canciones>{
        return cancionesPersona
    }
    fun addPersona(canciones:Canciones){
        cancionesPersona.add(canciones)
    }
    fun delPersona(canciones: Canciones){
        val tempLista = ArrayList(cancionesPersona)
        tempLista.forEach(){
            if(it.Titulo == canciones.Titulo){
                cancionesPersona.remove(it)
            }
        }
    }
    fun setLogeado(user:Int){
        logeado=user
        cancionesPersona.clear()
    }
    fun getLogeado():Int?{
        return logeado
    }
    fun getUsers():MutableList<String>{
        return listaUsers
    }
    fun AnyadirUsers(nuser:String){
        listaUsers.add(nuser)
    }
    fun AnyadirPassword(npassword:String){
        listaPassowrd.add(npassword)
    }
}
