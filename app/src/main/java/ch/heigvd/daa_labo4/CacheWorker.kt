package ch.heigvd.daa_labo4

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.io.File

class CacheWorker(appContext: Context, workerParams: WorkerParameters)
    : Worker(appContext, workerParams) {
    override fun doWork(): Result {
        val file = File(applicationContext.externalCacheDir, "myfile.txt")
        println(file.readText())
        file.deleteRecursively()
        //applicationContext.externalCacheDir?.deleteRecursively()
        return Result.success()
    }

    fun addToImageToExternalCache(byteArray: ByteArray)  {
        val file = File(applicationContext.externalCacheDir, "myfile.txt")
        file.writeBytes(byteArray)
    }
}
