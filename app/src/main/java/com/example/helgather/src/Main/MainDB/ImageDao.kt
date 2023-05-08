package com.example.helgather.src.Main.MainDB

import androidx.lifecycle.LiveData
import com.example.helgather.src.Main.MainDB.ImageEntity
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(image: ImageEntity): Long

    @Query("SELECT * FROM images ORDER BY id DESC LIMIT 1")
    fun getLastImage(): ImageEntity?

    @Query("SELECT * FROM images")
    fun getAllImages(): LiveData<List<ImageEntity>>
}


//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insert(image: ImageEntity)
//    @Query("SELECT * FROM images ORDER BY id DESC LIMIT 1")
//    fun getLastImage(): ImageEntity?


//    @Query("SELECT * FROM images")
//    fun getAllImages(): LiveData<List<ImageEntity>>

