package com.ahr.gigihfinalproject.data.worker

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.ahr.gigihfinalproject.MainActivity
import com.ahr.gigihfinalproject.R
import com.ahr.gigihfinalproject.domain.model.Resource
import com.ahr.gigihfinalproject.domain.repository.DisasterRepository
import com.ahr.gigihfinalproject.util.DataDummy
import com.ahr.gigihfinalproject.util.emptyString
import com.ahr.gigihfinalproject.util.toTmaMonitoringTimeFormat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.withContext

@ExperimentalPermissionsApi
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterialApi::class
)
@HiltWorker
class TmaMonitorWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted params: WorkerParameters,
    private val disasterRepository: DisasterRepository
) : CoroutineWorker(context, params) {

    private val notificationChannelId = "BencanaAppNotificationChannelId"
    private val notificationChannelName = "BencanaAppNotificationChannelName"

    override suspend fun doWork(): Result {

        val isNotificationPermissionGranted = ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED

        if (isNotificationPermissionGranted) {
            withContext(Dispatchers.IO) {
                disasterRepository.getTmaMonitoring().collectLatest { result ->
                    when (result) {
                        Resource.Idling -> {}
                        Resource.Loading -> {}
                        is Resource.Error -> {}
                        is Resource.Success -> {
                            val firstTmaMonitorResponse = result.data.firstOrNull() ?: DataDummy.getTmaMonitoringGeometryDummy()
                            val tmaMonitor = firstTmaMonitorResponse.properties
                            val title = tmaMonitor.gaugeid
                            val content = tmaMonitor.observations.firstOrNull()?.let {
                                context.getString(
                                    R.string.content_tma_monitoring_format,
                                    it.f4,
                                    it.f1.toTmaMonitoringTimeFormat(),
                                    tmaMonitor.gaugenameid
                                )
                            }
                            with(NotificationManagerCompat.from(applicationContext)) {
                                notify(0, createNotification(title, content))
                            }
                        }
                    }
                }
            }
        }

        return Result.success()
    }

    override suspend fun getForegroundInfo(): ForegroundInfo {
        return ForegroundInfo(
            0, createNotification()
        )
    }

    private fun createNotification(
        title: String = emptyString(),
        content: String? = null
    ): Notification {
        createNotificationChannel()
        val mainActivityIntent = Intent(
            applicationContext,
            MainActivity::class.java
        )

        val pendingIntentFlag = PendingIntent.FLAG_IMMUTABLE

        val mainActivityPendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            mainActivityIntent,
            pendingIntentFlag
        )

        return NotificationCompat.Builder(
            applicationContext,
            notificationChannelId
        )
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(title)
            .setContentText(content)
            .setContentIntent(mainActivityPendingIntent)
            .setAutoCancel(true)
            .build()

    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val notificationChannel = NotificationChannel(
                notificationChannelId,
                notificationChannelName,
                NotificationManager.IMPORTANCE_DEFAULT,
            )

            val notificationManager: NotificationManager? =
                getSystemService(
                    applicationContext,
                    NotificationManager::class.java
                )

            notificationManager?.createNotificationChannel(
                notificationChannel
            )
        }
    }
}