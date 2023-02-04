package org.piramalswasthya.sakhi.work

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.NetworkType
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import org.piramalswasthya.sakhi.database.shared_preferences.PreferenceDao
import org.piramalswasthya.sakhi.network.interceptors.TokenInsertTmcInterceptor
import org.piramalswasthya.sakhi.repositories.BenRepo
import timber.log.Timber
import java.net.SocketTimeoutException


@HiltWorker
class GenerateBenIdsWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
    private val benRepo: BenRepo,
    private val preferenceDao: PreferenceDao,
) : CoroutineWorker(appContext, params) {

    companion object {
        const val name = "GenBenIDWorker"
        val constraint = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

    }


    override suspend fun doWork(): Result {
        init()
        try {
            benRepo.getBenIdsGeneratedFromServer()
            return Result.success()
        } catch (e: SocketTimeoutException) {
            Timber.e("Caught Exception for Gen Ben iD worker $e")
            return Result.failure()
        }
    }

    private fun init() {
        if (TokenInsertTmcInterceptor.getToken() == "")
            TokenInsertTmcInterceptor.setToken(preferenceDao.getPrimaryApiToken()!!)
    }
}