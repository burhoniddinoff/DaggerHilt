package com.example.daggerhilt.presentation.add_update

import com.example.daggerhilt.model.Post

sealed class AddUpdateState {
    object Idle: AddUpdateState()
    object Loading: AddUpdateState()
    object SuccessUpdated: AddUpdateState()
    object SuccessCreated: AddUpdateState()
    data class Error(val message: String): AddUpdateState()
    data class SuccessPostGot(val post: Post): AddUpdateState()
}