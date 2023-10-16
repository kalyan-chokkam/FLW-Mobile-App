package org.piramalswasthya.sakhi.ui.home_activity.incentives

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.piramalswasthya.sakhi.database.shared_preferences.PreferenceDao
import org.piramalswasthya.sakhi.helpers.setToStartOfTheDay
import org.piramalswasthya.sakhi.model.IncentiveDomain
import org.piramalswasthya.sakhi.model.getDateStrFromLong
import org.piramalswasthya.sakhi.repositories.IncentiveRepo
import java.util.Calendar
import javax.inject.Inject


@HiltViewModel
class IncentivesViewModel @Inject constructor(
    pref: PreferenceDao,
    incentiveRepo: IncentiveRepo
) : ViewModel() {

    private val _lastUpdated: Long = pref.lastIncentivePullTimestamp
    val lastUpdated: String
        get() = getDateStrFromLong(_lastUpdated)!!
    private val sourceIncentiveList = incentiveRepo.list.map { it.map { it.asDomainModel() } }

    val initStart = Calendar.getInstance().apply {
        set(Calendar.DAY_OF_MONTH, 1)
        setToStartOfTheDay()
    }.timeInMillis

    val initEnd = Calendar.getInstance().apply {
        setToStartOfTheDay()
    }.timeInMillis

    private val _from = MutableStateFlow(initStart)
    val from: Flow<Long>
        get() = _from

    private val _to = MutableStateFlow(initEnd)

    val to: Flow<Long>
        get() = _to


    private val range = MutableStateFlow(Pair(initStart, initEnd))

    val incentiveList: Flow<List<IncentiveDomain>> =
        sourceIncentiveList.combine(range) { list, range ->

            list.filter { it.record.createdDate in (range.first..range.second) }
        }

    fun setRange(from: Long, to: Long) {
        viewModelScope.launch {
            range.emit(Pair(from, to))
            _from.emit(from)
            _to.emit(to)
        }
    }

}