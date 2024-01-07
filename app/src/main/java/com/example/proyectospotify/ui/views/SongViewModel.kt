package com.example.proyectospotify.ui.views

import android.content.ContentResolver
import android.content.Context
import android.content.res.Resources
import android.media.browse.MediaBrowser
import android.net.Uri
import androidx.annotation.AnyRes
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.SimpleExoPlayer
import androidx.room.util.copy
import com.example.proyectospotify.R
import com.example.proyectospotify.ui.dataclass.Canciones
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.random.Random

open class SongViewModel:ViewModel() {

    private val _songState: MutableStateFlow<ExoPlayer?> = MutableStateFlow(null)
    val songState = _songState.asStateFlow()

    //Duracion de la cancion
    private val _duracion = MutableStateFlow(0)
    val duracion = _duracion.asStateFlow()

    //Progreso actual de la cancion
    private val _progreso = MutableStateFlow(0)
    val progreso = _progreso.asStateFlow()

    private val _canciones: MutableList<Canciones> = mutableListOf()
    private val _cancionesFlow = MutableStateFlow(_canciones)
    val canciones: StateFlow<List<Canciones>> = _cancionesFlow

    var _indice = 5
    private val _cancion = MutableStateFlow(canciones)
    val cancion = _cancion.asStateFlow()

    //Cancion actual
    private val _actual = MutableStateFlow(R.raw.extras)
    val actual = _actual.asStateFlow()
    fun CargaCanciones(
        Canciones:ArrayList<Canciones>){
        _canciones.addAll(Canciones.toMutableList())
    }
    fun CancionCurso(): Canciones {
        return _canciones[_indice]
    }
    fun crearExo(context: Context){
        _songState.value = ExoPlayer.Builder(context).build()
        _songState.value!!.prepare()
        _songState.value!!.playWhenReady = true
    }
    fun play(context:Context){
        val mediaItem = MediaItem.fromUri(obtenerRuta(context,_actual.value))
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
        if(!_songState.value!!.isPlaying){
            _songState.value!!.play()
        }else{
            _songState.value!!.pause()
        }
    }
    // Este método se llama cuando el VM se destruya.
    override fun onCleared() {
        _songState.value!!.release()
        super.onCleared()
    }

    fun PausarOSeguirMusica(context: Context) {
        /* TODO: Si el reproductor esta sonando, lo pauso. Si no, lo reproduzco */

        if(!_songState.value!!.isPlaying){
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
        _indice++
        if(_indice >= _canciones.size){
            _indice = 0
        }else{
            _actual.value =_canciones[_indice].Cancion
        }

        _songState.value!!.stop()
        _songState.value!!.clearMediaItems()

        val mediaItem = MediaItem.fromUri(obtenerRuta(context,_actual.value ))
        _songState.value!!.setMediaItem(mediaItem)
        _songState.value!!.prepare()
        _songState.value!!.playWhenReady = true
    }
    fun previousSong(context: Context) {

        /* TODO: 1 - Cambiar la cancion actual y parar el mediaPlayer
         *  2 - Limpiar al _exoPlayer de los mediaItems que tenga
         *  3 - Crear mediaItem con la cancion actual
         *  4 - Establecer dicho mediaItem
         *  5 - Preparar el reproductor y activar el playWhenReady
        */

        //Si el índice es menor que 0 (-1) pasa a la última cancion
        _indice--
        if(_indice <= 0){
            _indice = _canciones.size
        }else{
            _actual.value =_canciones[_indice].Cancion
        }

        _songState.value!!.stop()
        _songState.value!!.clearMediaItems()

        val mediaItem = MediaItem.fromUri(obtenerRuta(context,_actual.value ))
        _songState.value!!.setMediaItem(mediaItem)

        _songState.value!!.prepare()
        _songState.value!!.playWhenReady = true

    }
    fun repeat(){
        System.out.println("Texto de ejemplo")
        _songState.value!!.repeatMode=SimpleExoPlayer.REPEAT_MODE_ALL
        _songState.value!!.playWhenReady = true
    }
    fun shuffle(){
        System.out.println("Numero aleatorio")

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



