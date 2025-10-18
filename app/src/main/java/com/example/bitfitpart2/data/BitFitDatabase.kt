package com.example.bitfitpart2.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [EntryEntity::class], version = 1, exportSchema = false)
abstract class BitFitDatabase : RoomDatabase() {
    abstract fun entryDao(): EntryDao

    companion object {
        @Volatile private var INSTANCE: BitFitDatabase? = null

        fun getInstance(context: Context): BitFitDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    BitFitDatabase::class.java,
                    "bitfit.db"
                ).build().also { INSTANCE = it }
            }
    }
}
