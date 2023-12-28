package com.example.proyectospotify.ui.views

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.media3.exoplayer.ExoPlayer
import androidx.room.util.copy
import com.example.proyectospotify.R
import com.example.proyectospotify.ui.dataclass.Canciones
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.random.Random

class SongViewModel:ViewModel() {
    private val _songState: MutableStateFlow<ExoPlayer?> = MutableStateFlow(null)
    val songState = _songState.asStateFlow()

    //

}