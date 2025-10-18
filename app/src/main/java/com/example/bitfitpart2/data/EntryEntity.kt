package com.example.bitfitpart2.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "entries")
data class EntryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val calories: Int,
    val date: Long,   // epoch millis
    val note: String? = null
)

