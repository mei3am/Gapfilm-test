package com.github.mei3am.test.interfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.mei3am.test.models.response.Content

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: Content)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list: List<Content>)

    @Query("DELETE FROM favorite WHERE contentId = :contentId")
    fun delete(contentId: Int)

    @Query("SELECT * FROM favorite")
    fun getAll(): List<Content>?
}