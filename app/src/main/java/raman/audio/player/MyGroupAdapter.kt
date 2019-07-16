package raman.audio.player

import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder

class MyGroupAdapter : GroupAdapter<ViewHolder>() {

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
}