package raman.audio.player

import android.content.Context
import android.media.MediaPlayer
import android.os.Handler
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView


class MyMediaPlayer {


    private var currentTime: TextView? = null
    private var duration: TextView? = null
    var player: MediaPlayer? = null
    private var context: Context? = null
    private var btnPlay: ImageButton? = null
    private var btnPause: ImageButton? = null
    private var seekBar: SeekBar? = null
    private var mediaPlayerPreparedListener: MediaPlayerPreparedListener? = null


    var id: Int = -1

    companion object {

        private var instance: MyMediaPlayer? = null

        var handler: Handler? = null
        var task: Runnable? = null


        fun getInstance(context: Context): MyMediaPlayer {
            if (instance == null) {

                instance = MyMediaPlayer()
                instance?.context = context
            }
            return instance!!
        }
    }

    fun setMediaPlayerPrepared(mediaPlayerPreparedListener: MediaPlayerPreparedListener) {
        this.mediaPlayerPreparedListener = mediaPlayerPreparedListener
    }

    fun playResource(id: Int, audioResource: Int, progress: Int = 0) {
        if (player != null) {
            if (player!!.isPlaying) {
                player!!.stop()
                player!!.reset()
                btnPause?.visibility = GONE
                btnPlay?.visibility = VISIBLE
                seekBar?.progress = 0
            }
            player = null
        }

        player = MediaPlayer.create(context, audioResource)
        instance?.id = id
        player?.start()
        player?.seekTo(progress)
        player?.setOnPreparedListener {
            seekBar?.max = it.duration
            it.seekTo(seekBar!!.progress)
            mediaPlayerPreparedListener?.onPrepared(it)
        }

        player?.setOnCompletionListener {
            btnPause?.visibility = GONE
            btnPlay?.visibility = VISIBLE
            seekBar?.progress = 0
            it.stop()
            MyGroupAdapter.currentPosition = -1
        }
    }


    fun pauseResource() {
        player?.pause()
    }

    fun isPlaying(audioId: Int): Boolean {
        if (player != null && audioId == id) {
            return player!!.isPlaying
        }
        return false
    }

    fun setViews(
        btnPlay: ImageButton?,
        btnPause: ImageButton?,
        seekBar: SeekBar?,
        currentTime: TextView?,
        duration: TextView?

    ) {
        this.btnPlay = btnPlay
        this.btnPause = btnPause
        this.seekBar = seekBar
        this.currentTime = currentTime
        this.duration = duration


    }


}