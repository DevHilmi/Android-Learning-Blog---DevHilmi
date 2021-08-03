package com.rizaldev.interactivetodolist.features.home.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rizaldev.interactivetodolist.R
import com.rizaldev.interactivetodolist.common.base.BaseFragment
import com.rizaldev.interactivetodolist.databinding.FragmentHomeBinding
import com.rizaldev.interactivetodolist.features.home.domain.model.Content
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

class HomeFragment :
    BaseFragment<HomeIntent, HomeAction, HomeState, HomeViewModel, FragmentHomeBinding>(
        HomeViewModel::class.java
    ) {

    private val adapter = HomeContentAdapter()

    override fun getViewModelImp(): HomeViewModel {
        val viewModel by viewModels<HomeViewModel>()
        return viewModel
    }

    override fun initUi() {
        val linearLayoutManager = LinearLayoutManager(context)
        binding.recyclerContent.layoutManager = linearLayoutManager
        binding.recyclerContent.adapter = adapter
    }

    override fun initData() {
        dispatchIntent(HomeIntent.LoadHomeContent)
    }

    override fun initEvent() {
    }

    override fun render(state: HomeState) {
        when (state) {
            is HomeState.Loading -> {

            }

            is HomeState.ContentData -> {
                adapter.addItem(state.data)
            }

            is HomeState.Exception -> {

            }
        }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    class HomeContentAdapter :
        RecyclerView.Adapter<HomeContentViewHolder>() {

        val content = mutableListOf<Content>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeContentViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_home_content, parent, false)
            return HomeContentViewHolder(view)
        }

        override fun onBindViewHolder(holder: HomeContentViewHolder, position: Int) {

        }

        fun addItem(content: List<Content>) {
            this.content.addAll(content)
            val insertIndex = when {
                content.isEmpty() -> 0
                else -> content.size - 1
            }
            notifyItemInserted(insertIndex)
        }

        override fun getItemCount(): Int {
            return content.size
        }
    }


}