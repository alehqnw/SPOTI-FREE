package com.example.proyectospotify.ui.views

import android.content.ContentResolver
import android.content.Context
import android.content.res.Resources
import android.media.browse.MediaBrowser
import android.net.Uri
import androidx.annotation.AnyRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.room.util.copy
import com.example.proyectospotify.R
import com.example.proyectospotify.ui.dataclass.Canciones
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.random.Random

class SongViewModel:ViewModel() {
    private val _songState: MutableStateFlow<ExoPlayer?> = MutableStateFlow(null)
    val songState = _songState.asStateFlow()
    //Cancion actual
    private val _actual = MutableStateFlow(R.raw.hellfire)
    val actual = _actual.asStateFlow()

    //Duracion de la cancion
    private val _duracion = MutableStateFlow(0)
    val duracion = _duracion.asStateFlow()

    //Progreso actual de la cancion
    private val _progreso = MutableStateFlow(0)
    val progreso = _progreso.asStateFlow()

    fun crearExo(context: Context){
        _songState.value = ExoPlayer.Builder(context).build()
        _songState.value!!.prepare()
        _songState.value!!.playWhenReady = true
    }
    fun play(context:Context){
        val mediaItem = MediaItem.fromUri(obtenerRuta(context,R.raw.hellfire))
        _songState.value!!.setMediaItem(mediaItem)

        _songState.value!!.addListener(object : Player.Listener{
            override fun onPlaybackStateChanged(playbackState: Int) {
                if(playbackState == Player.STATE_READY){
                    // El Player está preparado para empezar la reproducción.
                    // Si playWhenReady es true, empezará a sonar la música.

                    _duracion.value = _songState.value!!.duration.toInt()

                    viewModelScope.launch {
                        /* TODO: Actualizar el progreso usando currentPosition cada segundo */
                        while(isActive){
                            _progreso.value = _songState.value!!.currentPosition.toInt()
                            delay(1000)
                        }

                    }
                }
                else if(playbackState == Player.STATE_BUFFERING){
                    //Meter lacancion para el buffering desgraciadamente no está listo pero, estamos en ello :)
                }
                else if(playbackState == Player.STATE_ENDED){
                    CambiarCancion(context)
                }
                else if(playbackState == Player.STATE_IDLE){
                    //El player se ha creado, pero no se ha lanzado la operacion prepared
                }
            }
        }
        )
    }
    // Este método se llama cuando el VM se destruya.
    override fun onCleared() {
        _songState.value!!.release()
        super.onCleared()
    }

    fun PausarOSeguirMusica() {
        /* TODO: Si el reproductor esta sonando, lo pauso. Si no, lo reproduzco */
        if(!_songState.value!!.isPlaying ){
            _songState.value!!.play()
        }else{
            _songState.value!!.pause()
        }
    }

    fun CambiarCancion(context: Context) {

        /* TODO: 1 - Cambiar la cancion actual y parar el mediaPlayer
         *  2 - Limpiar al _exoPlayer de los mediaItems que tenga
         *  3 - Crear mediaItem con la cancion actual
         *  4 - Establecer dicho mediaItem
         *  5 - Preparar el reproductor y activar el playWhenReady
        */
        if(_actual.value == R.raw.hellfire){
            _actual.value = R.raw.extras
        }else{
            _actual.value = R.raw.hellfire
        }

        _songState.value!!.stop()
        _songState.value!!.clearMediaItems()

        val mediaItem = MediaItem.fromUri(obtenerRuta(context,_actual.value ))
        _songState.value!!.setMediaItem(mediaItem)

        _songState.value!!.prepare()
        _songState.value!!.playWhenReady = true

    }
    @Throws(Resources.NotFoundException::class)
    fun obtenerRuta(context: Context, @AnyRes resId: Int): Uri {
        val res: Resources = context.resources
        return Uri.parse(
            ContentResolver.SCHEME_ANDROID_RESOURCE +
                    "://" + res.getResourcePackageName(resId)
                    + '/' + res.getResourceTypeName(resId)
                    + '/' + res.getResourceEntryName(resId)
        )
    }

}



