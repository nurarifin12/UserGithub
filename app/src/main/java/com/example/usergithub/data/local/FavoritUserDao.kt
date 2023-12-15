package com.example.usergithub.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoritUserDao {
    @Insert
    suspend fun addToFavorit(favoritUser: FavoritUser)

    @Query("SELECT * FROM favorit_user")
    fun getFavorit(): LiveData<List<FavoritUser>>

    @Query("SELECT count(*) FROM favorit_user WHERE favorit_user.id = :id")
    suspend fun checkUser(id: Int): Int

    @Query("DELETE FROM favorit_user WHERE favorit_user.id = :id")
    suspend fun removeFavorit(id: Int): Int

}