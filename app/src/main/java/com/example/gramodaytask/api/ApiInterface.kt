package com.example.gramodaytask.api

import com.example.gramodaytask.model.Repository
import com.example.gramodaytask.model.RepositoryBranchCommits
import com.example.gramodaytask.model.RepositoryBranchIssues
import com.example.gramodaytask.model.RepositoryBranches
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET("repos/{owner}/{repo}")
    fun getData(
        @Path("owner") owner: String?,
        @Path("repo") repositoryName: String
    ): Call<Repository>

    @GET("repos/{owner}/{repo}/branches")
    fun getBranches(
        @Path("owner") owner: String?,
        @Path("repo") repositoryName: String
    ): Call<RepositoryBranches>

    @GET("repos/{owner}/{repo}/commits")
    fun getBranchCommits(
        @Path("owner") owner: String?,
        @Path("repo") repositoryName: String,
        @Query("branchSHA") branchSHA: String
    ): Call<RepositoryBranchCommits>

    @GET("repos/{owner}/{repo}/issues?state=open")
    fun getBranchIssues(
        @Path("owner") owner: String?,
        @Path("repo") repositoryName: String
    ): Call<RepositoryBranchIssues>

    @GET("users/{username}")
    fun getUser(
        @Path("username") username: String?
    ): Call<JsonObject>

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