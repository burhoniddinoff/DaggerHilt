package com.example.daggerhilt.network

import com.example.daggerhilt.model.Post
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("posts")
    suspend fun getAllPosts(): Response<Post>

    @PATCH("posts/{id}")
    suspend fun updatePost(@Path("id") id: Int): Response<Post>

    @DELETE("posts/{id}")
    suspend fun deletePost(@Path("id") id: Int): ResponseBody

    @POST("posts")
    suspend fun createPost(@Body post: Post): ResponseBody
}