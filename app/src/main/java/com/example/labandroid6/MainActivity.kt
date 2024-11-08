package com.example.labandroid6

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        Timber.plant(Timber.DebugTree())

        Timber.d("Start")
        val  showPictureButton: Button = findViewById(R.id.btn_show_pic)

        val url = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQwRgAxpivszzF5Ib7Xe_wMZWhmg4xWidvZwQ&s"
        Timber.d(url)
        showPictureButton.setOnClickListener(){
            val intent =  Intent(
                this,
                PicActivity::class.java
            ).apply {
                putExtra("linkToPicture",url)
            }


            startActivity(intent)

        }
    }
}