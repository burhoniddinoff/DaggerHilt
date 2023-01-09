package com.example.daggerhilt.repository

import com.example.daggerhilt.model.Post
import com.example.daggerhilt.network.ApiService
import com.example.daggerhilt.util.Response
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private var apiService: ApiService
) : PostRepository {

    override suspend fun getAllRemotePost(): Flow<Response<List<Post>>> = flow {
        try {
            emit(Response.Loading)
            delay(500L)
            val response = apiService.getAllPosts()
            if (response.isSuccessful) {
                emit(Response.Success(response.body()!!))
            }
        } catch (e: Exception) {
            emit(Response.Error(e.message!!))
        }
    }

    override suspend fun createPost(post: Post): Boolean {
        return try {
            var response = apiService.createPost(post)
            response.code() == 201
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun updatePost(id: Int): Boolean {
        return apiService.updatePost(id).isSuccessful
    }

    override suspend fun deletePost(id: Int): Boolean {
        return apiService.deletePost(id).isSuccessful

    }
}