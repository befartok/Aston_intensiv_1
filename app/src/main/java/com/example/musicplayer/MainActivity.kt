package com.example.musicplayer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ListView
import android.widget.Toast

// TODO: добавить нотификации
class MainActivity : AppCompatActivity() {
    private lateinit var buttonPlay: ImageButton
    private lateinit var buttonStop: ImageButton
    private lateinit var buttonPause: ImageButton
    private lateinit var buttonRewind: ImageButton
    private lateinit var buttonForward: ImageButton
    private lateinit var listView: ListView
    private var tracksPosition = 0
    private lateinit var tracksName: String
    private var nameList = ArrayList<String>()
    private lateinit var intent : Intent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonPlay = findViewById<ImageButton>(R.id.buttonPlay)
        buttonStop = findViewById<ImageButton>(R.id.buttonStop)
        buttonPause = findViewById<ImageButton>(R.id.buttonPause)
        buttonRewind = findViewById<ImageButton>(R.id.buttonRewind)
        buttonForward = findViewById<ImageButton>(R.id.buttonForward)
        listView = findViewById<ListView>(R.id.listView)

        tracksName = resources.getResourceEntryName(R.raw.vivaldi_winter_allegro).toString()

        initPlayList()
        initPlayer()

    }

    private fun initPlayList() {

        nameList.add(resources.getResourceEntryName(R.raw.vivaldi_winter_allegro).toString())
        nameList.add(resources.getResourceEntryName(R.raw.blondie_atomic).toString())
        nameList.add(
            resources.getResourceEntryName(R.raw.f_hardy_tous_les_garcons_et_les_fille)
                .toString()
        )

        listView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, nameList)

        clickPlaylist()

    }

    private fun clickPlaylist() {
        listView.setOnItemClickListener { parent, view, position, id ->

            tracksName = nameList.get(position)
            tracksPosition = position

            val serviceClass = MusicPlayerService::class.java
            val intent = Intent(this, serviceClass)

            intent.putExtra("buttonsType", "Play")
            intent.putExtra("trName", tracksName)

            stopService(intent)
            startService(intent)
        }
    }

    private fun initPlayer() {

        val serviceClass = MusicPlayerService::class.java

        intent = Intent(this, serviceClass)

        clickButtonPlay()
        clickButtonStop()
        clickButtonPause()
        clickButtonRewind()
        clickButtonForward()
    }

    private fun clickButtonForward() {
        buttonForward.setOnClickListener {
            intent.putExtra("buttonsType", "Play")

            if (tracksPosition == nameList.size - 1) {
                tracksPosition = 0
            } else tracksPosition += 1

            tracksName = nameList[tracksPosition]
            intent.putExtra("trName", tracksName)

            stopService(intent)
            startService(intent)
        }
    }

    private fun clickButtonRewind() {
        buttonRewind.setOnClickListener {

            intent.putExtra("buttonsType", "Play")

            if (tracksPosition == 0) {
                tracksPosition = nameList.size - 1
            } else tracksPosition -= 1

            tracksName = nameList[tracksPosition]

            intent.putExtra("trName", tracksName)

            stopService(intent)
            startService(intent)
        }
    }

    private fun clickButtonPause() {
        buttonPause.setOnClickListener {
            intent.putExtra("buttonsType", "Pause")
            startService(intent)
        }
    }

    private fun clickButtonStop() {
        buttonStop.setOnClickListener {
            intent.putExtra("buttonsType", "Stop")
            stopService(intent)
        }
    }

    private fun clickButtonPlay() {
        buttonPlay.setOnClickListener {
            intent.putExtra("buttonsType", "Play")
            intent.putExtra("trName", tracksName)
            startService(intent)
        }
    }
}

