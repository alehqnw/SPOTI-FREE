package com.example.proyectospotify.ui.views

import androidx.lifecycle.ViewModel
import com.example.proyectospotify.R
import com.example.proyectospotify.ui.dataclass.Canciones

class BBDD: ViewModel() {
    val _canciones : MutableList<Canciones> = mutableListOf()
    val listaUsers : MutableList<String> = mutableListOf()
    val listaPassowrd : MutableList<String> = mutableListOf()
    val cancionesPersona : MutableList<Canciones> = mutableListOf()
    fun CargaUsers(){
        listaUsers.add("Alejandro")
        listaUsers.add("Alverto")
        listaUsers.add("Juan")
        listaPassowrd.add("1234")//Contraseña de Alejandro y las demás con tal
        listaPassowrd.add("12345")
        listaPassowrd.add("123456")
    }
    fun addPersona(canciones:Canciones){
        cancionesPersona.add(canciones)
    }
    fun delPersona(canciones: Canciones){
        cancionesPersona.remove(canciones)
    }
    fun AnyadirUsers(nuser:String){
        listaUsers.add(nuser)
    }
    fun AnyadirPassword(npassword:String){
        listaPassowrd.add(npassword)
    }
    fun CargaCanciones(){
        _canciones.add(Canciones("Hellfire","ROCK", R.raw.hellfire, R.drawable.hellfire,"4:31"))
        _canciones.add(Canciones("Extras","ROCK", R.raw.extras, R.drawable.extras,"5:36"))
        _canciones.add(
            Canciones("Like a Weed","ROCK",
                R.raw.likeaweed,
                R.drawable.likeaweed,"4:45")
        )
        _canciones.add(Canciones("ALEMAN","TRAP", R.raw.aleman, R.drawable.aleman,"2:57"))
        _canciones.add(Canciones("WANDA","REGGAETON", R.raw.wanda, R.drawable.quevedo,"3:00"))
        _canciones.add(
            Canciones("QUEVEDOBZRP","REGGAETON",
                R.raw.quevedobzrp,
                R.drawable.bzrpportada,"3:24")
        )
        _canciones.add(Canciones("MANDANGA","TROLL", R.raw.mandanga, R.drawable.mandanga,"2:50"))
        _canciones.add(
            Canciones("LUCESAZULES","REGGAETON",
                R.raw.lucesazules,
                R.drawable.quevedo,"2:44")
        )
        _canciones.add(
            Canciones("ELADIOCARRION","TRAP",
                R.raw.eladiocarrion,
                R.drawable.heladio,"2:52")
        )
        _canciones.add(Canciones("DILLOM","TRAP", R.raw.dillom, R.drawable.dillom,"2:45"))
        _canciones.add(
            Canciones("CABALLOHOMOSEXUAL","TROLL",
                R.raw.caballohomosexual,
                R.drawable.caballo,"0:46")
        )
        _canciones.add(Canciones("CHAMBA","TROLL", R.raw.chamba, R.drawable.chambaportada,"2:45"))

    }
}