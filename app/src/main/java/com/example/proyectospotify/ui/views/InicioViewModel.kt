package com.example.proyectospotify.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.databinding.ObservableList
import androidx.lifecycle.ViewModel
import com.example.proyectospotify.R
import com.example.proyectospotify.ui.dataclass.Canciones
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class InicioViewModel: ViewModel() {
    private var _nombre = MutableStateFlow("Alejandro")
    val nombre = _nombre.asStateFlow()
    private val _canciones= MutableStateFlow(arrayListOf<Canciones>())

    val canciones = _canciones.asStateFlow()
    fun actualizarNombre(nuevoNombre: String){
        _nombre.value = nuevoNombre
    }
    fun CargaCanciones(){
        _canciones.value.add(Canciones("Hellfire","ROCK", R.raw.hellfire,R.drawable.hellfire,"4:31"))
        _canciones.value.add(Canciones("Extras","ROCK", R.raw.extras,R.drawable.extras,"5:36"))
        _canciones.value.add(Canciones("Like a Weed","ROCK",R.raw.likeaweed,R.drawable.likeaweed,"4:45"))
        _canciones.value.add(Canciones("ALEMAN","TRAP",R.raw.aleman,R.drawable.aleman,"2:57"))
        _canciones.value.add(Canciones("WANDA","REGGAETON",R.raw.wanda,R.drawable.quevedo,"3:00"))
        _canciones.value.add(Canciones("QUEVEDOBZRP","REGGAETON",R.raw.quevedobzrp,R.drawable.bzrpportada,"3:24"))
        _canciones.value.add(Canciones("MANDANGA","TROLL",R.raw.mandanga,R.drawable.mandanga,"2:50"))
        _canciones.value.add(Canciones("LUCESAZULES","REGGAETON",R.raw.lucesazules,R.drawable.quevedo,"2:44"))
        _canciones.value.add(Canciones("ELADIOCARRION","TRAP",R.raw.eladiocarrion,R.drawable.heladio,"2:52"))
        _canciones.value.add(Canciones("DILLOM","TRAP",R.raw.dillom,R.drawable.dillom,"2:45"))
        _canciones.value.add(Canciones("CABALLOHOMOSEXUAL","TROLL",R.raw.caballohomosexual,R.drawable.caballo,"0:46"))
        _canciones.value.add(Canciones("CHAMBA","TROLL",R.raw.chamba,R.drawable.chambaportada,"2:45"))
    }
    fun DescargaCanciones():ArrayList<Canciones>{
        return canciones.value
    }
    @Composable
    fun CancionesCard(Titulo:String, Imagen:Int){
        Card(colors = CardDefaults.cardColors(Color.Red)
        ) {
            Column() {
                Image(
                    painter = painterResource(id = Imagen),
                    contentDescription = null,
                    modifier = Modifier
                        .size(200.dp)
                )
                //Falta la banda negra del texto
            }
        }
    }
}