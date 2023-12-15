package com.example.usergithub.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.lifecycle.ViewModel
import com.example.usergithub.api.ApiClient
import com.example.usergithub.data.local.FavoritUser
import com.example.usergithub.data.local.FavoritUserDao
import com.example.usergithub.data.local.UserDatabase
import com.example.usergithub.data.response.DetailUserResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class DetailUserViewModel(application: Application): AndroidViewModel(application){
    val user = MutableLiveData<DetailUserResponse>()

    private var userDao: FavoritUserDao?
    private var userDb: UserDatabase?

    init {
        userDb= UserDatabase.getDatabase(application)
        userDao=userDb?.favoritUserDao()
    }

    fun setUserDetail( username: String ){
        ApiClient.apiInstance
            .getDetailUser(username)
            .enqueue(object: Callback<DetailUserResponse>{
                override fun onResponse(call: Call<DetailUserResponse>, response: Response<DetailUserResponse>) {

                    if (response.isSuccessful){

                        user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                 Log.d("Failure" , t.message.toString())
                }

            })
    }

    fun getUserDetail(): LiveData<DetailUserResponse>{
        return user
    }

    fun addToFavorit(username: String, id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            val user = FavoritUser(
                username,
                id
            )
            userDao?.addToFavorit(user)
        }
    }

    suspend fun checkUser(id: Int) = userDao?.checkUser(id)

    fun removeFavorit(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFavorit(id)
        }
    }


}