package ch.heigvd.daa_labo4


import android.os.Bundle
import android.os.Environment
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import ch.heigvd.daa_labo4.databinding.ActivityMainBinding
import java.io.File


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val adapter = ListAdapter(lifecycleScope)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Binding views
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = GridLayoutManager(this, 3)

        val items = mutableListOf<Int>()
        for (i in 1..10_000) {
            items += i
        }
        adapter.items = items
    }

    private fun isExternalStorageWritable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

    /**
     * Clear cache when pressing the icon in the bar menu
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.cached_icon -> {
                val workManager = WorkManager.getInstance(applicationContext)
                val myWorkRequest = OneTimeWorkRequestBuilder<CacheWorker>().build()
                workManager.enqueue(myWorkRequest)
                true
            }
            else -> {
                true
            }
        }
    }

    /**
     * Inflate the bar menu with our additional functionality
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}

