package com.example.daggerhilt.repository

import com.example.daggerhilt.database.PostDao
import com.example.daggerhilt.model.Post
import com.example.daggerhilt.network.ApiService
import com.example.daggerhilt.util.Response
import com.example.daggerhilt.util.toPost
import com.example.daggerhilt.util.toPostEntity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val dao: PostDao
) : PostRepository {
    override suspend fun getAllRemotePosts(): Flow<Response<List<Post>>> = flow {
        try {
            emit(Response.Loading)
            delay(500L)
            val response = apiService.getAllPosts()
            if (response.isSuccessful) {
                emit(Response.Success(response.body()!!))
            }
        } catch (e: Exception) {
            emit(Response.Error(e.message.toString()))
        }
    }

    override suspend fun getPostById(id: Int): Flow<Response<Post>> = flow {
        try {
            emit(Response.Loading)
            delay(500L)
            val response = apiService.getPostById(id)
            if (response.isSuccessful) {
                emit(Response.Success(response.body()!!))
            }
        } catch (e: Exception) {
            emit(Response.Error(e.message.toString()))
        }
    }

    override suspend fun createPost(post: Post): Flow<Boolean> = flow {
        try {
            val response = apiService.createPost(post)
            emit(response.code() == 201)
        } catch (e: Exception) {
            emit(false)
        }
    }

    override suspend fun updatePost(id: Int): Flow<Boolean> = flow {
        emit(apiService.updatePost(id).isSuccessful)
    }

    override suspend fun deletePost(id: Int): Flow<Boolean> = flow {
        emit(apiService.deletePost(id).isSuccessful)
    }

    override suspend fun savePostList(list: List<Post>) {
        dao.savePostList(list.map { it.toPostEntity() })
    }

    override suspend fun clearData() {
        dao.clearData()
    }

    override fun getAllLocalPosts(): Flow<List<Post>> = flow {
        dao.getLocalPostList().collect { post ->
            emit(post.map { it.toPost() })
        }
    }
}