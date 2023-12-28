package com.example.proyectospotify.ui.pantalla

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.proyectospotify.R
import com.example.proyectospotify.ui.views.SongViewModel


@Composable
fun SongScreen(viewModel: SongViewModel){
    // Obtenemos el estado de la canción desde el ViewModel
    val songState by viewModel.songState.collectAsState()

    // Creamos una columna que ocupará todo el espacio disponible
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Mostramos el título de la canción
        Text(
            text = songState.title,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Mostramos la imagen de la canción
        Image(
            painter = painterResource(R.drawable.ggst),
            contentDescription = null,
            modifier = Modifier.size(200.dp)
        )

        // Mostramos una barra deslizante para controlar el progreso de la canción
        Slider(
            value = songState.progress,
            onValueChange = { viewModel.seekTo(it) },
            valueRange = 0f..songState.duration.toFloat(),
            modifier = Modifier.padding(top = 16.dp, bottom = 32.dp)
        )

        // Creamos una fila para los botones de control de reproducción
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Botón para ir a la canción anterior
            IconButton(onClick = { viewModel.previousSong() }) {
                Icon(Icons.Filled.ArrowBack, contentDescription = null)
            }

            // Botón para reproducir/pausar la canción
            IconButton(onClick = { viewModel.togglePlayPause() }) {
                if (songState.isPlaying) {
                    Icon(Icons.Filled.Warning, contentDescription = null)
                } else {
                    Icon(Icons.Filled.PlayArrow, contentDescription = null)
                }
            }

            // Botón para ir a la siguiente canción
            IconButton(onClick = { viewModel.nextSong() }) {
                Icon(Icons.Filled.ArrowForward, contentDescription = null)
            }
        }

        // Creamos una segunda fila para los botones de shuffle y repeat
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Botón para activar/desactivar el shuffle
            IconButton(onClick = { viewModel.shuffle() }) {
                //Icon(Icons.Filled.Shuffle, contentDescription = null)
            }

            // Botón para activar/desactivar el repeat
            IconButton(onClick = { viewModel.repeat() }) {
                Icon(Icons.Filled.Refresh, contentDescription = null)
            }
        }
    }
}

