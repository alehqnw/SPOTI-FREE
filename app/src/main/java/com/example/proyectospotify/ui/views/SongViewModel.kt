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
    var _indice = 0


    //Cancion actual
    private val _actual = MutableStateFlow(0)
    val actual = _actual.asStateFlow()
    fun CargaCanciones(){
        _canciones.add(Canciones("Hellfire","ROCK", R.raw.hellfire,R.drawable.hellfire,"4:31"))
        _canciones.add(Canciones("Extras","ROCK", R.raw.extras,R.drawable.extras,"5:36"))
        _canciones.add(Canciones("Like a Weed","ROCK",R.raw.likeaweed,R.drawable.likeaweed,"4:45"))
        _canciones.add(Canciones("ALEMAN","TRAP",R.raw.aleman,R.drawable.aleman,"2:57"))
        _canciones.add(Canciones("WANDA","REGGAETON",R.raw.wanda,R.drawable.quevedo,"3:00"))
        _canciones.add(Canciones("QUEVEDOBZRP","REGGAETON",R.raw.quevedobzrp,R.drawable.bzrpportada,"3:24"))
        _canciones.add(Canciones("MANDANGA","TROLL",R.raw.mandanga,R.drawable.mandanga,"2:50"))
        _canciones.add(Canciones("LUCESAZULES","REGGAETON",R.raw.lucesazules,R.drawable.quevedo,"2:44"))
        _canciones.add(Canciones("ELADIOCARRION","TRAP",R.raw.eladiocarrion,R.drawable.heladio,"2:52"))
        _canciones.add(Canciones("DILLOM","TRAP",R.raw.dillom,R.drawable.dillom,"2:45"))
        _canciones.add(Canciones("CABALLOHOMOSEXUAL","TROLL",R.raw.caballohomosexual,R.drawable.caballo,"0:46"))
        _canciones.add(Canciones("CHAMBA","TROLL",R.raw.chamba,R.drawable.chambaportada,"2:45"))

        _actual.value=_canciones[_indice].Cancion
    }
    fun CancionCurso(): Canciones {
        return _canciones[_indice]
    }
    fun crearExo(context: Context){
        _songState.value = ExoPlayer.Builder(context).build()
        _songState.value!!.prepare()
        _songState.value!!.playWhenReady
    }
    fun play(context:Context){
        val mediaItem = MediaItem.fromUri(obtenerRuta(context,_actual.value))
        _songState.value!!.setMediaItem(mediaItem)
        _songState.value!!.prepare()
        _songState.value!!.playWhenReady = true
        _songState.value!!.addListener(object : Player.Listener{
            override fun onPlaybackStateChanged(playbackState: Int) {
                if(playbackState == Player.STATE_READY){
                    // El Player está preparado para empezar la reproducción.
                    // Si playWhenReady es true, empezará a sonar la música.

                    _duracion.value = _songState.value!!.duration.toInt()/1000

                    viewModelScope.launch {
                        /* TODO: Actualizar el progreso usando currentPosition cada segundo */
                        while(isActive){
                            _progreso.value = _songState.value!!.currentPosition.toInt()/1000
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

    fun PausarOSeguirMusica(context: Context) {
        /* TODO: Si el reproductor esta sonando, lo pauso. Si no, lo reproduzco */
        if(!_songState.value!!.isPlaying){
            _songState.value!!.playWhenReady=true
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
        if(_indice > _canciones.lastIndex){
            _indice=0

        }
        _actual.value =_canciones[_indice].Cancion
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
        if(_indice  < 0){
            _indice = _canciones.lastIndex
            _actual.value =_canciones[_indice].Cancion
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

        _songState.value!!.repeatMode=ExoPlayer.REPEAT_MODE_ONE
    }
    fun shuffle(context:Context){
        var temporal=(Math.random()*_canciones.lastIndex).toInt()
        if(temporal>= _indice){
            temporal++
        }
        _indice=temporal
        _actual.value =_canciones[_indice].Cancion
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



