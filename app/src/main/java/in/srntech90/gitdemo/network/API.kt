package `in`.srntech90.gitdemo.network

import `in`.srntech90.gitdemo.model.GitItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
Created by Tanuj.Sareen on 23,January,2020

 API Methods
 **/
interface API {

    @GET("developers/")
    fun callDevelopers(@Query("language") language: String, @Query("since") since: String): Call<ArrayList<GitItem>>
}