package com.semashko.dbroom.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.semashko.dbroom.entities.TouristsRoutes

@Dao
interface TouristsRoutesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setTouristsRoutes(touristsRoutes: TouristsRoutes)

    @Query("SELECT * from tourists_routes_table ORDER BY name ASC")
    fun getTouristsRoutes(): List<TouristsRoutes>

    @Query("DELETE FROM tourists_routes_table")
    fun deleteAll()
}