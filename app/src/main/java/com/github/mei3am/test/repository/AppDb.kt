package com.github.mei3am.test.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.mei3am.test.interfaces.FavoriteDao
import com.github.mei3am.test.models.response.Content

@Database(entities = [Content::class], version = 1)
abstract class AppDb: RoomDatabase() {

    abstract fun billBasketDao(): FavoriteDao
}