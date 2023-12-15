package com.example.usergithub.api

import com.example.usergithub.data.response.DetailUserResponse
import com.example.usergithub.data.response.User
import com.example.usergithub.data.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearchUser(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>
}