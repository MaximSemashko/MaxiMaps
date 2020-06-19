package com.semashko.dbroom.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.semashko.dbroom.entities.Attractions

@Dao
interface AttractionsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setAttractions(attractions: Attractions)

    @Query("SELECT * from attractions_table ORDER BY name ASC")
    fun getAttractions(): List<Attractions>

    @Query("DELETE FROM attractions_table")
    fun deleteAll()
}