package raman.audio.player

import com.xwray.groupie.ViewHolder

interface RecyclerViewListener {

    fun onViewAttached(holder: ViewHolder?)

    fun onViewRecycled(holder: ViewHolder)
}