package com.example.labandroid6

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val  showPictureButton: Button = findViewById(R.id.btn_show_pic)

        showPictureButton.setOnClickListener(){
            val intent = Intent(
                this,
                PicActivity::class.java
            )
            intent.putExtra("linkToPicture,","[https://farm66.staticflickr.com/65535/54121573517_d6659baa91_z.jpg")

            startActivity(intent)

        }
    }
}