package com.example.labandroid6

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide

class PicViewer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.pic_viewer)
        val imageView: ImageView =  findViewById(R.id.picView)
        val picLink = intent.getStringExtra("linkToPicture")



        Glide.with(this)
            .load(picLink)
            .into(imageView);
    }
}