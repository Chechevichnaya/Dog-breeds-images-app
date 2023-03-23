package com.example.dogsbreedapp.data.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dogsbreedapp.data.FavoriteImagesDao

@Database(entities = [FavoriteImageEntity::class], version = 1, exportSchema = false)
abstract class DogsBreedDatabase : RoomDatabase() {

    abstract fun favoriteImagesDao(): FavoriteImagesDao

    companion object {
        @Volatile
        private var Instance: DogsBreedDatabase? = null

        fun getDatabase(context: Context): DogsBreedDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, DogsBreedDatabase::class.java, "favorite_images_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}