package com.example.labandroid6

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar


class PicViewer : AppCompatActivity() {
    public var picLink = " "
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.pic_viewer)
        val imageView: ImageView =  findViewById(R.id.picView)
        picLink = intent.getStringExtra("linkToPicture").toString()
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)



        Glide.with(this)
            .load(picLink)
            .into(imageView);
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item_favorite -> {
                val snackbar = Snackbar
                    .make(findViewById(R.id.picView), "Избранное", Snackbar.LENGTH_LONG)
                snackbar.show()
                val returnIntent:Intent = Intent(this, MainActivity::class.java).apply {
                    putExtra("link", picLink)
                    putExtra("fav", true)
                }
                setResult(RESULT_OK, returnIntent)
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}