package raman.audio.player

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.xwray.groupie.Section
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val adapter = MyGroupAdapter()
        val section = Section()
        section.add(AudioItem2(R.raw.abc_1))
        section.add(AudioItem2(R.raw.abc_2))
        section.add(AudioItem2(R.raw.abc_3))
        section.add(AudioItem2(R.raw.abc_4))
        section.add(AudioItem2(R.raw.abc_5))
        section.add(AudioItem2(R.raw.abc_19))
        section.add(AudioItem2(R.raw.abc_6))
        section.add(AudioItem2(R.raw.abc_7))
        section.add(AudioItem2(R.raw.abc_8))
        section.add(AudioItem2(R.raw.abc_9))
        section.add(AudioItem2(R.raw.abc_10))
        section.add(AudioItem2(R.raw.abc_11))
        section.add(AudioItem2(R.raw.abc_12))
        section.add(AudioItem2(R.raw.abc_13))
        section.add(AudioItem2(R.raw.abc_14))
        section.add(AudioItem2(R.raw.abc_15))
        section.add(AudioItem2(R.raw.abc_16))
        section.add(AudioItem2(R.raw.abc_17))
        section.add(AudioItem2(R.raw.abc_18))
        adapter.add(section)
        audioRecyclerView.adapter = adapter
    }
}
