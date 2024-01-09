package com.example.proyectospotify.ui.pantalla

import android.annotation.SuppressLint
import android.graphics.Paint
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
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope

import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.proyectospotify.R
import com.example.proyectospotify.ui.dataclass.Canciones
import com.example.proyectospotify.ui.views.InicioViewModel
import com.example.proyectospotify.ui.views.SongViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch



@SuppressLint("StateFlowValueCalledInComposition", "CoroutineCreationDuringComposition")
@Composable
fun SongScreen(){
    val viewModel: SongViewModel = viewModel()
    // Obtenemos el estado de la canción desde el ViewModel
    val contexto = LocalContext.current
    val songstate = viewModel.songState.collectAsState().value
    val corutinaScope = rememberCoroutineScope()

    // Creamos una columna que ocupará todo el espacio disponible
    LaunchedEffect(key1 = Unit){
        viewModel.crearExo(contexto)
        viewModel.CargaCanciones()
        viewModel.play(contexto)
    }

    if(songstate != null){
        var cancionEncurso by remember { mutableStateOf(viewModel.CancionCurso())}
        println(viewModel._indice)
        corutinaScope.launch {
            cancionEncurso=viewModel.CancionCurso()
        }
        //viewModel.play(contexto)

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Mostramos la imagen de la canción

            Image(
                painter = painterResource(cancionEncurso.Imagen),
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
                IconButton(onClick = { viewModel.PausarOSeguirMusica(contexto) }) {
                    if(viewModel.progreso.collectAsState().value.toFloat() == viewModel.duracion.collectAsState().value.toFloat()){
                        cancionEncurso=viewModel.CancionCurso()
                    }
                    if (viewModel.songState.value?.isPlaying == true) {

                        Icon(painterResource(id = R.drawable.ic_pause), contentDescription = null,
                            modifier = Modifier
                                .size(100.dp)
                                .background(Color.Red))
                    } else {
                        Icon(
                            painterResource(id = R.drawable.playarrownegro)
                            , contentDescription = null,
                            Modifier
                                .size(100.dp)
                                .background(color = Color.Red))
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
                modifier = Modifier.fillMaxWidth()
            ) {
                // Botón para activar/desactivar el shuffle
                IconButton(onClick = { viewModel.shuffle(contexto);cancionEncurso=viewModel.CancionCurso() }) {
                    Icon(painterResource(id = R.drawable.ic_shuffle), contentDescription = null)
                }

                // Botón para activar/desactivar el repeat
                IconButton(onClick = { viewModel.repeat() }) {
                    Icon(Icons.Filled.Refresh, contentDescription = null)
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
