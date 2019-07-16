package raman.audio.player

import android.media.MediaPlayer

interface MediaPlayerPreparedListener {

    fun onPrepared(mp: MediaPlayer)
}