package ch.heigvd.daa_labo4

import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import java.io.File
import java.io.IOException
import java.net.URL
import java.util.concurrent.Executors


class ListAdapter(_coroutineScope: LifecycleCoroutineScope, _items : List<Int> = listOf()) :
    RecyclerView.Adapter<ListAdapter.ViewHolder>() {
    private val myThreadDispatcher = Executors.newFixedThreadPool(3).asCoroutineDispatcher()
    private val coroutineScope = _coroutineScope
    private val cacheMaxTimeStorage = 300_000
    var items = listOf<Int>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    init {
        items = _items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item, parent, false), viewType
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }


    override fun getItemCount() = items.size

    inner class ViewHolder(view: View, private val viewType: Int) : RecyclerView.ViewHolder(view) {
        private val loader = view.findViewById<ProgressBar>(R.id.loader)
        private val image = view.findViewById<ImageView>(R.id.image)
        private var job: Job? = null
        private val pictures = File(view.context.externalCacheDir, "pictures")

        init {
            pictures.mkdirs()
        }

        fun bind(itemPosition: Int) {
            job?.cancel()
            image.visibility = View.INVISIBLE
            loader.visibility = View.VISIBLE
            val url = URL("https://daa.iict.ch/images/$itemPosition.jpg")

            job = coroutineScope.launch {
                var bytes : ByteArray?
                val picture = File(pictures, "$itemPosition.jpg")

                // Download the picture if needed into external cache, read it otherwise
                if (!picture.exists() ||  System.currentTimeMillis() - picture.lastModified() > cacheMaxTimeStorage) {
                    bytes = downloadImage(url)
                    saveToCache(bytes!!, pictures, "$itemPosition.jpg")
                } else {
                    bytes = getFromCache(picture)
                }

                // Decode bytes and display the image
                val bmp = decodeImage(bytes)
                displayImage(image, bmp)
                loader.visibility = View.INVISIBLE
                image.visibility = View.VISIBLE
            }
        }
    }

    suspend fun getFromCache(file: File) : ByteArray? = withContext(Dispatchers.IO) {
        try {
            file.readBytes()
        } catch (e: IOException) {
            Log.w(ContentValues.TAG, "Exception while downloading image from cache", e)
            null
        }
    }

    suspend fun downloadImage(url : URL) : ByteArray? = withContext(Dispatchers.IO) {
        try {
            // delay(1000) // or Thread.sleep(1000)
            url.readBytes()
        } catch (e: IOException) {
            Log.w(ContentValues.TAG, "Exception while downloading image", e)
            null
        }
    }

    suspend fun downloadImageCustomDispatcher(url : URL) : ByteArray? = withContext(myThreadDispatcher) {
        try {
            // delay(1000) // or Thread.sleep(1000)
            url.readBytes()
        } catch (e: IOException) {
            Log.w(ContentValues.TAG, "Exception while downloading image", e)
            null
        }
    }

    suspend fun saveToCache(bytes: ByteArray, cacheRoot: File?, fileName : String) = withContext(Dispatchers.IO) {
        try {
            File(cacheRoot, fileName).outputStream().use {
                it.write(bytes)
            }
        } catch (e: IOException) {
            Log.w(ContentValues.TAG, "Exception while saving image to cache", e)
            null
        }
    }

    suspend fun decodeImage(bytes : ByteArray?) : Bitmap? = withContext(Dispatchers.Default) {
        try {
            BitmapFactory.decodeByteArray(bytes, 0, bytes?.size ?: 0)
        } catch (e: IOException) {
            Log.w(ContentValues.TAG, "Exception while decoding image", e)
            null
        }
    }

    suspend fun displayImage(myImage : ImageView, bmp : Bitmap?) = withContext(Dispatchers.Main) {
        if(bmp != null)
            myImage.setImageBitmap(bmp)
        else
            myImage.setImageResource(android.R.color.transparent)
    }
}
