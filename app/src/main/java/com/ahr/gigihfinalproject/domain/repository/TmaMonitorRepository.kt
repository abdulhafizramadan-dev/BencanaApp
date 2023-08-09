package com.ahr.gigihfinalproject.domain.repository

interface TmaMonitorRepository {

    suspend fun runOneTimeTmaMonitor()

    suspend fun runPeriodicTmaMonitor()

    suspend fun cancelPeriodicTmaMonitor()

}