package raman.audio.player

import android.media.MediaPlayer
import android.os.Handler
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.audio_item.view.*
import java.util.concurrent.TimeUnit
import javax.xml.datatype.DatatypeConstants.MINUTES


class AudioItem2(private val audioRes: Int) : Item<ViewHolder>() {

    lateinit var myMediaPlayer: MyMediaPlayer
    lateinit var seekBar: SeekBar
    lateinit var btnPlay: ImageButton
    lateinit var btnPause: ImageButton
    lateinit var currentTime: TextView
    lateinit var duration: TextView


    override fun getLayout(): Int = R.layout.audio_item

    override fun bind(viewHolder: ViewHolder, position: Int) {

        btnPlay = viewHolder.itemView.btnPlay
        btnPause = viewHolder.itemView.btnPause
        seekBar = viewHolder.itemView.seekBar
        currentTime = viewHolder.itemView.txtStartTime
        duration = viewHolder.itemView.txtSongTime

        myMediaPlayer = MyMediaPlayer.getInstance(viewHolder.itemView.context)


        if (myMediaPlayer.isPlaying(position)) {
            btnPlay.visibility = GONE
            btnPause.visibility = VISIBLE

        } else {
            btnPlay.visibility = VISIBLE
            btnPause.visibility = GONE
            seekBar.progress = 0
        }


        btnPlay.setOnClickListener {
            btnPlay.visibility = GONE
            btnPause.visibility = VISIBLE
            MyGroupAdapter.currentPosition = position
            myMediaPlayer.playResource(position, audioRes, seekBar.progress)
            myMediaPlayer.setViews(
                btnPlay = btnPlay,
                btnPause = btnPause,
                seekBar = seekBar,
                duration = duration,
                currentTime = currentTime
            )

            myMediaPlayer.setMediaPlayerPrepared(object : MediaPlayerPreparedListener {
                override fun onPrepared(mp: MediaPlayer) {
                    startUpdater(position)
                    val finalTime = mp.duration
                    duration.text = String.format(
                        "%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes(finalTime.toLong()),
                        TimeUnit.MILLISECONDS.toSeconds(finalTime.toLong()) - TimeUnit.MINUTES.toSeconds(
                            TimeUnit.MILLISECONDS.toMinutes(
                                finalTime.toLong()
                            )
                        )
                    )
                }

            })

        }

        btnPause.setOnClickListener {
            it.visibility = GONE
            btnPlay.visibility = VISIBLE
            myMediaPlayer.pauseResource()
            stopUpdater()
            MyGroupAdapter.currentPosition = -1
        }



        MyGroupAdapter.setRecyclerViewListener(object : RecyclerViewListener {
            override fun onViewAttached(holder: ViewHolder?) {
                startUpdater(position)
                Log.d("XXXX", "I am attached")
            }

            override fun onViewRecycled(holder: ViewHolder) {
                stopUpdater()
                Log.d("XXXX", "I am Removed")
            }

        })

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

                if (fromUser) {
                    if (myMediaPlayer.player != null && myMediaPlayer.isPlaying(position))
                        myMediaPlayer.player!!.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })


    }


    private fun startUpdater(position: Int) {
        if (MyMediaPlayer.handler == null) {
            MyMediaPlayer.handler = Handler()
        }

        MyMediaPlayer.task = object : Runnable {
            override fun run() {
                if (myMediaPlayer.isPlaying(position)) {
                    seekBar.max = myMediaPlayer.player!!.duration

                    val startTime = myMediaPlayer.player!!.currentPosition
                    currentTime.text = String.format(
                        "%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes(startTime.toLong()),
                        TimeUnit.MILLISECONDS.toSeconds(startTime.toLong()) - TimeUnit.MINUTES.toSeconds(
                            TimeUnit.MILLISECONDS.toMinutes(
                                startTime.toLong()
                            )
                        )
                    )
                    seekBar.progress = startTime
                    MyMediaPlayer.handler?.postDelayed(this, 50)
                } else {
                    seekBar.max = myMediaPlayer.player!!.duration
                    seekBar.progress = 0
                    stopUpdater()
                }

            }
        }

        MyMediaPlayer.handler?.postDelayed(MyMediaPlayer.task, 50)
    }

    private fun stopUpdater() {
        MyMediaPlayer.handler?.removeCallbacks(MyMediaPlayer.task, null)
        MyMediaPlayer.handler = null
        MyMediaPlayer.task = null
    }
}