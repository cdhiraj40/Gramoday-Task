package com.example.gramodaytask.api

import com.example.gramodaytask.model.Repository
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {
    @GET("repos/{owner}/{repo}")
    fun getData(
        @Path("owner") owner: String?,
        @Path("repo") repositoryName: String
    ): Call<Repository>

    companion object {
        fun create(): ApiInterface {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(URL.github)
                .build()
            return retrofit.create(ApiInterface::class.java)
        }
    }
}