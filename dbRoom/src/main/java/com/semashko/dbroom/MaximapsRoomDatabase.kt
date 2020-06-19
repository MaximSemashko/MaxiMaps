package com.semashko.dbroom

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.semashko.dbroom.dao.AttractionsDao
import com.semashko.dbroom.dao.MansionsDao
import com.semashko.dbroom.dao.TouristsRoutesDao
import com.semashko.dbroom.entities.Attractions
import com.semashko.dbroom.entities.Mansions
import com.semashko.dbroom.entities.TouristsRoutes

@Database(
    entities = [
        Attractions::class,
        Mansions::class,
        TouristsRoutes::class
    ], version = 2, exportSchema = true
)
abstract class MaximapsRoomDatabase : RoomDatabase() {

    abstract fun touristsRoutesDao(): TouristsRoutesDao

    abstract fun mansionsDao(): MansionsDao

    abstract fun attractionsDao(): AttractionsDao

    companion object {
        operator fun invoke(context: Context) =
            try {
                buildDatabase(context).also { it.openHelper.writableDatabase }
            } catch (ex: IllegalStateException) {
                dropOldDatabase(context)

                buildDatabase(context)
            }

        private fun buildDatabase(context: Context): MaximapsRoomDatabase {
            return Room.databaseBuilder(
                context,
                MaximapsRoomDatabase::class.java,
                "MaximapsRoomDatabase"
            )
                .enableMultiInstanceInvalidation()
                .build()
                .also { it.openHelper.setWriteAheadLoggingEnabled(true) }
        }

        private fun dropOldDatabase(context: Context) {
            context.deleteDatabase("MaximapsRoomDatabase")
        }
    }
}