package com.example.bitfitpart2.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface EntryDao {
    @Query("SELECT * FROM entries ORDER BY date DESC")
    fun getAllEntriesFlow(): Flow<List<EntryEntity>>

    @Insert
    suspend fun insert(entry: EntryEntity)

    @Query("DELETE FROM entries")
    suspend fun clearAll()
}

