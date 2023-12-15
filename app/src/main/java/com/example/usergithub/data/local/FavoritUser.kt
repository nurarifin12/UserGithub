package com.example.usergithub.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favorit_user")
data class FavoritUser(
    val login: String,
    @PrimaryKey
    val id: Int
):Serializable
