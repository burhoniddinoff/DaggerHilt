package com.example.daggerhilt.presentation.post_list

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.daggerhilt.R
import com.example.daggerhilt.adapter.PostListAdapter
import com.example.daggerhilt.databinding.FragmentPostListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PostListFragment : Fragment(R.layout.fragment_post_list) {
    private var _binding: FragmentPostListBinding? = null
    private val viewModel: PostListViewModel by viewModels()
    private val postListAdapter by lazy { PostListAdapter() }
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPostListBinding.bind(view)

        initViews()

    }


    private fun initViews() {
        binding.rv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = postListAdapter
        }
        lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    is PostListState.Idle -> Unit
                    is PostListState.Loading -> {
                        binding.progressBar.isVisible = true
                    }
                    is PostListState.Error -> {
                        Log.d("PostListFragment", "initViews: ${it.message}")
                        binding.progressBar.isVisible = false
                    }
                    is PostListState.Success -> {
                        binding.progressBar.isVisible = false
                        postListAdapter.submitList(it.list)
                    }
                }
            }
        }
        postListAdapter.onClick = {
            val post = bundleOf("id" to it.id)
            findNavController().navigate(R.id.action_postListFragment_to_detailFragment, post)
        }
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_postListFragment_to_addUpdateFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}