package com.example.daggerhilt.presentation.post_list

import com.example.daggerhilt.model.Post

sealed class PostListState {
    object Idle : PostListState()
    object Loading : PostListState()
    data class Error(val message: String) : PostListState()
    data class Success(val list: List<Post>) : PostListState()
}