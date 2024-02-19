package com.example.proyectospotify.ui.pantalla

import SongViewModel
import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.proyectospotify.R
import com.example.proyectospotify.database.database
import com.example.proyectospotify.database.storage
import com.example.proyectospotify.ui.views.BBDD
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import io.grpc.Context.Storage
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("StateFlowValueCalledInComposition", "CoroutineCreationDuringComposition")
@Composable
fun SongScreen(navController: NavController,indice:Int?,esFavorito:Boolean){
    val viewModel: SongViewModel = viewModel()
    // Obtenemos el estado de la canción desde el ViewModel
    val contexto = LocalContext.current
    val songstate = viewModel.songState.collectAsState().value
    val corutinaScope = rememberCoroutineScope()
    var playPause by remember{ mutableStateOf(false)}
    var loopBool by remember{ mutableStateOf(false)}
    var addBool by remember{ mutableStateOf(false)}
    var storage = FirebaseStorage.getInstance()
    var songRef:StorageReference
    var downloadurl:String? = ""
    var bd = database.listaCanciones.collectAsState().value
    // Creamos una columna que ocupará todo el espacio disponible
    LaunchedEffect(key1 = Unit){
        if(indice!=null){
//            songRef= storage
//                .getReferenceFromUrl("gs://proyectospotifybien.appspot.com/raw/extras.mp3")
//              .child("extras.mp3")
//            songRef.downloadUrl.addOnSuccessListener {
//                downloadurl=it.toString()
//
//            }
            bd.forEach{
///               var titulo = R.raw.extras.toString()
                println("musica "+downloadurl)
                println(it.Cancion)//AÑADIR A LA BD EL INT DE LA MUSICA
            }
            songRef.downloadUrl.addOnFailureListener{
                println(it.message)
            }
            viewModel._indice=indice
            //println("SongScreen indice "+viewModel._indice)
        }

//        if(downloadurl!=null){
//            bd.forEach {
//                it.Cancion= downloadurl as String
//                println(it.Titulo + " cancion url "+it.Cancion)
//            }


        viewModel.crearExo(contexto)
        viewModel.CargaCanciones(esFavorito)
        viewModel.play(contexto)
    }

    if(songstate != null){
        var cancionEncurso by remember { mutableStateOf(viewModel.CancionCurso())}
        corutinaScope.launch {
            cancionEncurso=viewModel.CancionCurso()
        }
        //viewModel.play(contexto)
        LaunchedEffect(key1 = cancionEncurso){
            loopBool=false
            addBool=false
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Mostramos la imagen de la canción
            Image(
                painter = painterResource(R.drawable.extras),
                contentDescription = null,
                modifier = Modifier.size(325.dp)
            )

            // Mostramos el título de la canción
            Text(
                text = cancionEncurso.Titulo,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier
                    .padding(start = 32.dp, top = 16.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Left,
                fontSize = 25.sp
            )
            // Mostramos una barra deslizante para controlar el progreso de la canción
            val progreso = viewModel.progreso.collectAsState().value.toFloat()
            //val duracionTotal = viewModel.duracion.collectAsState().value.toFloat()

            Slider(
                value = viewModel.progreso.collectAsState().value.toFloat(),
                onValueChange = {nuevoValor ->
                    // hay que coge rl nuevo valor
                                songstate.seekTo(nuevoValor.toLong() * 1000)
                },
                valueRange = 0f..viewModel.duracion.collectAsState().value.toFloat(),
                steps = viewModel.duracion.collectAsState().value,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 16.dp)
                    .fillMaxWidth()
                    .widthIn(0.dp, 300.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = formatTime(progreso.toLong()))
                Text(text = cancionEncurso.duracion)
            }

            // Creamos una fila para los botones de control de reproducción
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Botón para ir a la canción anterior
                IconButton(onClick = { viewModel.previousSong(contexto);cancionEncurso=viewModel.CancionCurso()}) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = null)
                }

                // Botón para reproducir/pausar la canción
                IconButton(
                    onClick = { viewModel.PausarOSeguirMusica(contexto); playPause = !playPause},
                    modifier = Modifier.background(if (playPause) Color.Red else Color.Transparent)
                    ) {
                    if(viewModel.progreso.collectAsState().value.toFloat() == viewModel.duracion.collectAsState().value.toFloat()){
                        cancionEncurso=viewModel.CancionCurso()
                    }
                    if (viewModel.songState.value?.isPlaying == true) {

                        Icon(painterResource(id = R.drawable.ic_pause), contentDescription = null,
                            modifier = Modifier
                                .size(100.dp)
                                )
                    } else {
                        Icon(
                            painterResource(id = R.drawable.playarrownegro)
                            , contentDescription = null,
                            Modifier
                                .size(100.dp)
                                )
                    }
                }

                // Botón para ir a la siguiente canción
                IconButton(onClick = { viewModel.CambiarCancion(contexto);cancionEncurso=viewModel.CancionCurso()}) {
                    Icon(Icons.Filled.ArrowForward, contentDescription = null)
                }
            }
            // Creamos una segunda fila para los botones de shuffle y repeat
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp)
            ) {
                // Botón para activar/desactivar el shuffle
                IconButton(onClick = { viewModel.shuffle(contexto);cancionEncurso=viewModel.CancionCurso() }) {
                    Icon(painterResource(id = R.drawable.ic_shuffle), contentDescription = null)
                }

                // Botón para activar/desactivar el repeat
                IconButton(onClick = { loopBool=!loopBool},
                    modifier = Modifier.background(if (loopBool) Color.Red else Color.Transparent)
                ) {
                    Icon(Icons.Filled.Refresh, contentDescription = null)
                    LaunchedEffect(loopBool){
                        if(loopBool){
                            viewModel.repeat()
                        }else{
                            viewModel.noRepeat()
                        }
                    }
                }

                if(!esFavorito){
                    IconButton(onClick = {addBool=!addBool},
                        modifier = Modifier.background(if (addBool) Color.Red else Color.Transparent)
                    ) {
                        Icon(Icons.Filled.Create, contentDescription = null)
                        LaunchedEffect(addBool){
                            if(addBool){
                                //BBDD.addPersona(cancionEncurso)

                            }else{
                                //BBDD.delPersona(cancionEncurso)
                            }
                            //println(BBDD.getPersona())
                        }

                    }
                }

            }
    }

    }
}

@Composable
fun formatTime(seconds: Long): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return "%02d:%02d".format(minutes, remainingSeconds)
}
