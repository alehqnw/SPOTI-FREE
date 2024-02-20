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
    private lateinit var listener: ListenerRegistration
    private lateinit var listenerUser: ListenerRegistration
    private val db = Firebase.firestore
    var _listaCanciones = MutableStateFlow(mutableStateListOf<CancionesDB>())
    var listaCanciones = _listaCanciones.asStateFlow()
    private var _listaUser = MutableStateFlow(mutableStateListOf<User>())
    var listaUsers = _listaUser.asStateFlow()
    var storage = storage()
    var currentUsuario = ""
    fun leer() {
        _listaCanciones.value.clear()
        listener = db.collection("songs").addSnapshotListener { datos, error ->
            if (error == null) {
                datos?.documentChanges?.forEach { cambio ->
                    if (cambio.type == DocumentChange.Type.ADDED) {
                        var cancion = cambio.document.toObject<CancionesDB>()
                        cancion.idCanciones = cambio.document.id
                        _listaCanciones.value.add(cancion)
                    } else if (cambio.type == DocumentChange.Type.MODIFIED) {
                        var cancion = cambio.document.toObject<CancionesDB>()
                        _listaCanciones.value[cambio.newIndex] = cancion
                    } else {
                        var cancion = cambio.document.toObject<CancionesDB>()
                        _listaCanciones.value.remove(cancion)
                    }
                }
            }
        }
    }

    fun leerUser() {
        _listaUser.value.clear()
        listenerUser = db.collection("users").addSnapshotListener { datos, error ->
            if (error == null) {
                datos?.documentChanges?.forEach { cambio ->
                    if (cambio.type == DocumentChange.Type.ADDED) {
                        var user = cambio.document.toObject<User>()
                        user.idUser = cambio.document.id
                        println(user.usuario)
                        _listaUser.value.add(user)
                    } else if (cambio.type == DocumentChange.Type.MODIFIED) {
                        var user = cambio.document.toObject<User>()
                        _listaUser.value[cambio.newIndex] = user

                    } else {
                        var user = cambio.document.toObject<User>()
                        _listaUser.value.remove(user)
                    }
                }
            }
        }
    }


    fun anyadirUser(user: String) {
        val usuario: User = User(user, arrayListOf(""))
        db.collection("users").add(usuario)
    }

    fun addSong(cancionTitle: String) {
        val usuario: User = User(currentUsuario, canciones = listOf(cancionTitle))
        db.collection("users")
            .whereEqualTo("usuario", currentUsuario)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    println("PreUser "+document.id)
                    val cancion = document.get("canciones") as ArrayList<String>
                    println("Dentro de array "+cancion)
                    println("Cancion $cancion añadida a $currentUsuario")
                    cancion.add(cancionTitle)

                    db.collection("users").document(document.id).update("canciones",cancion)
                }
            }
    }
    fun removeSong(cancionTitle: String) {
        val usuario: User = User(currentUsuario, canciones = listOf(cancionTitle))
        db.collection("users")
            .whereEqualTo("usuario", currentUsuario)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    println("PreUser "+document.id)
                    val cancion = document.get("canciones") as ArrayList<String>
                    println("Dentro de array "+cancion)
                    println("Cancion $cancion añadida a $currentUsuario")
                    cancion.removeIf { it == cancionTitle }
                    db.collection("users").document(document.id).update("canciones", cancion)
                }
            }
    }
    fun borrarFav(user: String, idCancion: String) {
        db.collection("listafav").document("admin").delete()
    }
}