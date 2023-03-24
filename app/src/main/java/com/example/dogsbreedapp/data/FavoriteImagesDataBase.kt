package com.example.dogsbreedapp.data.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dogsbreedapp.data.FavoriteImagesDao

@Database(entities = [FavoriteImageEntity::class], version = 1, exportSchema = false)
abstract class FavoriteImagesDataBase : RoomDatabase() {

    abstract fun favoriteImagesDao(): FavoriteImagesDao

    companion object {
        @Volatile
        private var Instance: FavoriteImagesDataBase? = null

        fun getDatabase(context: Context): FavoriteImagesDataBase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, FavoriteImagesDataBase::class.java, "favorite_images_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}