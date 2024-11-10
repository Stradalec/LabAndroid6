package com.example.labandroid6

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import timber.log.Timber


class PicActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.pic_layout)
        val imageView: ImageView =  findViewById(R.id.picView)
        Timber.plant(Timber.DebugTree())
        Timber.d("picture")

        val picLink = intent.getStringExtra("linkToPicture")

        Timber.d("picture: $picLink")


            Glide.with(this)
                .load(picLink)
                .into(imageView);


    }
}