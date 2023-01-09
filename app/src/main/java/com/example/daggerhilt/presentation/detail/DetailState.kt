package com.example.daggerhilt.presentation.detail

import com.example.daggerhilt.model.Post

sealed class DetailState {
    object Loading : DetailState()
    data class Error(val message: String) : DetailState()
    object SuccessDeleted: DetailState()
    data class Success(val post: Post) : DetailState()
}