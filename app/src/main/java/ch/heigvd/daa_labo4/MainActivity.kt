package ch.heigvd.daa_labo4


import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.work.*
import ch.heigvd.daa_labo4.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit


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

        // Set periodic cache clean
        val myPeriodicWorkRequest = PeriodicWorkRequestBuilder<CacheWorker>(1, TimeUnit.MINUTES)
            .setBackoffCriteria(
                BackoffPolicy.EXPONENTIAL,
                PeriodicWorkRequest.MIN_BACKOFF_MILLIS, TimeUnit.MILLISECONDS)
            .build()

        WorkManager.getInstance(applicationContext).enqueue(myPeriodicWorkRequest)
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
                adapter.notifyDataSetChanged()
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
