package com.example.proyectospotify.database

import androidx.compose.runtime.mutableStateListOf
import com.example.proyectospotify.ui.dataclass.CancionesDB
import com.example.proyectospotify.ui.dataclass.User
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object database {
    private lateinit var listener :ListenerRegistration
    private lateinit var listenerUser :ListenerRegistration
    private val db = Firebase.firestore
    var _listaCanciones = MutableStateFlow(mutableStateListOf<CancionesDB>())
    var listaCanciones = _listaCanciones.asStateFlow()
    private var _listaUser = MutableStateFlow(mutableStateListOf<User>())
    var listaUsers = _listaUser.asStateFlow()
    var storage = storage()
    fun leer(){
        listener = db.collection("songs").addSnapshotListener{
            datos,error->
            if(error==null){
                datos?.documentChanges?.forEach{cambio->
                    if(cambio.type== DocumentChange.Type.ADDED){
                        var cancion = cambio.document.toObject<CancionesDB>()
                        cancion.idCanciones= cambio.document.id
                        _listaCanciones.value.add(cancion)
                    }else if(cambio.type==DocumentChange.Type.MODIFIED){
                        var cancion = cambio.document.toObject<CancionesDB>()
                        _listaCanciones.value[cambio.newIndex]=cancion
                    }else{
                        var cancion = cambio.document.toObject<CancionesDB>()
                        _listaCanciones.value.remove(cancion)
                    }
                }
            }
        }
    }
    fun leerUser(){
        listenerUser = db.collection("users").addSnapshotListener{
                datos,error->
            if(error==null){
                datos?.documentChanges?.forEach{cambio->
                    if(cambio.type== DocumentChange.Type.ADDED){
                        var user = cambio.document.toObject<User>()
                        user.idUser=cambio.document.id
                        println(user.usuario)
                        _listaUser.value.add(user)
                    }else{
                        var user = cambio.document.toObject<User>()
                        _listaUser.value.remove(user)
                    }
                }
            }
        }
    }
    fun borrar(){
        listener.remove()
    }
    fun anyadirUser(user:String,password:String){
        val usuario:User = User(user,password)
        db.collection("users").add(usuario)
    }
    fun borrarFav(user:String,idCancion:String){
        db.collection("listafav").document("admin").delete()
    }
}