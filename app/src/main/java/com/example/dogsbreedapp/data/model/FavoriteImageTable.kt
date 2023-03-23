package com.example.dogsbreedapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "favorite_images")
data class FavoriteImageTable(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val image_uri:String,
    val breed: String
)
