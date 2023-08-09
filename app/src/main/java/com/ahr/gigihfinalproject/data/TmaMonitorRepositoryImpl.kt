package com.ahr.gigihfinalproject.data

import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.ahr.gigihfinalproject.data.worker.TmaMonitorWorker
import com.ahr.gigihfinalproject.domain.repository.TmaMonitorRepository
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalPermissionsApi
@Singleton
class TmaMonitorRepositoryImpl @Inject constructor(
    private val workManager: WorkManager
) : TmaMonitorRepository {

    private val notificationBaseWaterWorkerName = "NotificationBaseWaterWorker"

    private val notificationConstraints get() = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    override suspend fun runOneTimeTmaMonitor() {
        val workRequest = OneTimeWorkRequestBuilder<TmaMonitorWorker>()
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .setConstraints(notificationConstraints)
            .build()

        workManager.enqueueUniqueWork(
            notificationBaseWaterWorkerName,
            ExistingWorkPolicy.KEEP,
            workRequest
        )
    }

    override suspend fun runPeriodicTmaMonitor() {
        val workRequest = PeriodicWorkRequestBuilder<TmaMonitorWorker>(
            repeatInterval = 1L,
            repeatIntervalTimeUnit = TimeUnit.HOURS
        ).build()
        workManager.enqueueUniquePeriodicWork(
            notificationBaseWaterWorkerName,
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }

    override suspend fun cancelPeriodicTmaMonitor() {
        workManager.cancelUniqueWork(notificationBaseWaterWorkerName)
    }
}