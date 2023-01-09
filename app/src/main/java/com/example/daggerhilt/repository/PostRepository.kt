package com.example.daggerhilt.repository

import com.example.daggerhilt.model.Post
import com.example.daggerhilt.util.Response
import kotlinx.coroutines.flow.Flow


interface PostRepository {
    // remote
    suspend fun getAllRemotePosts(): Flow<Response<List<Post>>>
    suspend fun createPost(post: Post): Flow<Boolean>
    suspend fun updatePost(id: Int): Flow<Boolean>
    suspend fun deletePost(id: Int): Flow<Boolean>
    suspend fun getPostById(id: Int): Flow<Response<Post>>

    // local
    suspend fun savePostList(list: List<Post>)
    suspend fun clearData()

}