package ch.heigvd.daa_labo4

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.io.File

class CacheWorker(appContext: Context, workerParams: WorkerParameters)
    : Worker(appContext, workerParams) {
    private val pictures = File(appContext.externalCacheDir, "pictures")

    override fun doWork(): Result {
        pictures.listFiles()?.forEach { it.delete()
        }
        println("cache cleaned")
        return Result.success()
    }
}
