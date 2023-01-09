package com.example.daggerhilt.presentation.post_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.daggerhilt.repository.PostRepository
import com.example.daggerhilt.util.NetworkHelper
import com.example.daggerhilt.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostListViewModel @Inject constructor(
    private val repository: PostRepository,
    private val helper: NetworkHelper
) : ViewModel() {
    private val _state: MutableStateFlow<PostListState> = MutableStateFlow(PostListState.Idle)
    val state: StateFlow<PostListState> get() = _state

    init {
        getAllPosts()
    }

    private fun getAllPosts() {
        viewModelScope.launch {
            if (helper.isNetworkConnected()) {
                repository.getAllRemotePosts().collect { response ->
                    when (response) {
                        is Response.Loading -> {
                            _state.update {
                                PostListState.Loading
                            }
                        }
                        is Response.Error -> {
                            _state.update {
                                PostListState.Error(response.error)
                            }
                        }
                        is Response.Success -> {
                            _state.update {
                                PostListState.Success(response.data)
                            }
                            repository.clearData()
                            repository.savePostList(response.data)
                        }
                    }
                }
            } else {
                repository.getAllLocalPosts().collect { list ->
                    _state.update {
                        PostListState.Success(list)
                    }
                }
            }
        }
    }
}