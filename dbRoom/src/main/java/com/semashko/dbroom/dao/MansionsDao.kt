package com.semashko.dbroom.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.semashko.dbroom.entities.Mansions

@Dao
interface MansionsDao {

    @Insert(onConflict = REPLACE)
    fun setMansions(mansions: Mansions)

    @Query("SELECT * from mansions_table ORDER BY name ASC")
    fun getMansions() : List<Mansions>

    @Query("DELETE FROM mansions_table")
    fun deleteAll()
}