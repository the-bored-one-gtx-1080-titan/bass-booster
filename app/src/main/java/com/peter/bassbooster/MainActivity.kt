package com.peter.bassbooster

import android.media.audiofx.BassBoost
import android.os.Bundle
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var bassBoost: BassBoost? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val seekBar = findViewById<SeekBar>(R.id.bassSeekBar)
        val statusText = findViewById<TextView>(R.id.statusText)

        try {
            bassBoost = BassBoost(0, 0)
            bassBoost?.enabled = true

            val currentStrength = bassBoost?.roundedStrength?.toInt() ?: 0
            seekBar.max = 1000
            seekBar.progress = currentStrength
            statusText.text = "Bass Strength: ${currentStrength / 10}%"

            seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    try {
                        bassBoost?.setStrength(progress.toShort())
                        statusText.text = "Bass Strength: ${progress / 10}%"
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            })
        } catch (e: Exception) {
            Toast.makeText(this, "Global audio session restriction.", Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        bassBoost?.release()
    }
}
