package org.piramalswasthya.sakhi.model

import org.piramalswasthya.sakhi.database.room.SyncState

data class SyncStatusCache(

    val name: String, val syncState: SyncState, val count: Int

)


fun List<SyncStatusCache>.asDomainModel(): List<SyncStatusDomain> {
    return groupBy { it.name }.map { mapEntry ->
        SyncStatusDomain(
            name = mapEntry.key,
            synced = mapEntry.value.firstOrNull { it.syncState == SyncState.SYNCED }?.count ?: 0,
            notSynced = mapEntry.value.firstOrNull { it.syncState == SyncState.UNSYNCED }?.count
                ?: 0,
            syncing = mapEntry.value.firstOrNull { it.syncState == SyncState.SYNCING }?.count ?: 0
        )
    }
}

data class SyncStatusDomain(
    val name: String,
    val synced: Int,
    val notSynced: Int,
    val syncing: Int,
    val totalCount: Int = synced + notSynced + syncing
)