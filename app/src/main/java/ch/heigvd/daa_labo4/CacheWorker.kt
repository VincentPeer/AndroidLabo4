package ch.heigvd.daa_labo4

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class CacheWorker(appContext: Context, workerParams: WorkerParameters)
    : Worker(appContext, workerParams) {
    override fun doWork(): Result {

        return Result.success()
    }
}