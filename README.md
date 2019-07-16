RecyclerView based Media Player (resolved multiple bar proccessing like other media palyers inside whatsapp, facebook etc ).

Steps : 

1 >  Create a Listener 
```
  interface RecyclerViewListener {
    fun onViewAttached(holder: ViewHolder?)
    fun onViewRecycled(holder: ViewHolder)
  }
  ```
  
2 >  Call both of these in RecyclerView / Groupie Adapter (To handle recycling)
```
  companion object {
        var currentPosition: Int = -1
        private var recyclerViewListener: RecyclerViewListener? = null
        fun setRecyclerViewListener(recyclerViewListener: RecyclerViewListener?) {
            this.recyclerViewListener = recyclerViewListener
        }
  }
  override fun onViewAttachedToWindow(holder: ViewHolder?) {
        if (holder != null && currentPosition == getAdapterPosition(holder.item)) {
            recyclerViewListener?.onViewAttached(holder)
        }
        super.onViewAttachedToWindow(holder)
  }

  override fun onViewRecycled(holder: ViewHolder) {
        if (currentPosition == getAdapterPosition(holder.item)) {
            recyclerViewListener?.onViewRecycled(holder)
        }
        super.onViewRecycled(holder)
  }
```
3 > Create a Runnable to update progress bar and Time
```
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
   ```     
        
 <p align="center">
<img align="left"  src="https://raw.githubusercontent.com/rahman2k9/media-player/master/app/src/main/res/drawable/Screenshot_20190717-011711_AudioPlayer.jpg" data-canonical-src="https://raw.githubusercontent.com/rahman2k9/media-player/master/app/src/main/res/drawable/Screenshot_20190717-011711_AudioPlayer.jpg" width="200" height="300" />



<img  align="left" src="https://raw.githubusercontent.com/rahman2k9/media-player/master/app/src/main/res/drawable/Screenshot_20190717-012819_AudioPlayer.jpg" data-canonical-src="https://raw.githubusercontent.com/rahman2k9/media-player/master/app/src/main/res/drawable/Screenshot_20190717-012819_AudioPlayer.jpg" width="200" height="300" />
</p>
