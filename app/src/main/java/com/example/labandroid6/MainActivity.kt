package com.example.labandroid6

import android.R.attr.data
import android.R.id.content
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import timber.log.Timber
import java.io.IOException


class MainActivity : AppCompatActivity() {

    private var adapter = MyAdapter(emptyList()) {
        startNewActivity(it)
    }


    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Timber.plant(Timber.DebugTree())

        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val data = result.data
                    val imageUrl = data?.getStringExtra("link")
                    val snackBar = Snackbar.make(
                        findViewById(content),
                        "Картинка добавлена в избранное",
                        Snackbar.LENGTH_SHORT
                    )
                    snackBar.setAction("Открыть") {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(imageUrl))
                        startActivity(intent)
                    }
                    snackBar.show()
                }
            }


        val recyclerView: RecyclerView = findViewById(R.id.rView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = adapter
        CoroutineScope(Dispatchers.IO).launch {
            val list =
                parsePhotos("https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=ff49fcd4d4a08aa6aafb6ea3de826464&tags=cat&format=json&nojsoncallback=1")
            withContext(Dispatchers.Main) {
                adapter.updateList(list)
            }
        }


    }


    private fun startNewActivity(url: String) {
        Timber.i(url)
        val pictureIntent =
            Intent(this, PicViewer::class.java).apply { putExtra("linkToPicture", url) }
        resultLauncher.launch(pictureIntent)
    }

    private fun parsePhotos(inputUrl: String): List<String> {
        val client = OkHttpClient()
        var photoLinks: List<String> = listOf("1")
        val request = Request.Builder()
            .url(inputUrl)
            .build()
        client.newCall(request).execute().body()?.string()
        val response = client.newCall(request).execute()
        val body = response.body()?.string()
        if (body != null) {
            val gson = GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()

            val wrapper = gson.fromJson(body, com.example.labandroid6.Wrapper::class.java)

            photoLinks = wrapper.photos.photo.map { photo ->
                "https://farm${photo.farm}.staticflickr.com/${photo.server}/${photo.id}_${photo.secret}_z.jpg"
            }
            Timber.i("Copied link: $photoLinks")

            wrapper.photos.photo.forEachIndexed { index, photo ->
                if (index % 5 == 0) {
                    Timber.d("ID: ${photo.id}, Owner: ${photo.owner}, Secret = ${photo.secret}, Server = ${photo.server}, Farm = ${photo.farm}, Title: ${photo.title}, IsPublic = ${photo.isItPublic}, IsFriend = ${photo.isFriend},IsFamily= ${photo.isFamily}")
                }
            }

        }
        return photoLinks
    }

}