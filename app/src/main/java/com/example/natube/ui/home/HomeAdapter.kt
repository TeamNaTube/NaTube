package com.example.natube.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.natube.databinding.FragmentHomeRvChipBinding
import com.example.natube.databinding.FragmentHomeRvItemBigBinding
import com.example.natube.databinding.FragmentHomeRvItemBinding
import com.example.natube.databinding.FragmentHomeTitleBinding

/**
 *  HomeFragment 에 화면 구성
 *  1. ItemViewType 찾기
 *  2. 해당 ViewType 에따른 ViewHolder 생성 및 연결
 *  3. 연결된 ViewHolder 에 저장한 값들 Binding
 */
class HomeAdapter(private val viewModel: HomeViewModel) : ListAdapter<HomeWidget, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    private lateinit var mContext: Context
    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HomeWidget>() {
            override fun areItemsTheSame(oldItem: HomeWidget, newItem: HomeWidget): Boolean {
                return oldItem::class == newItem::class
            }

            override fun areContentsTheSame(oldItem: HomeWidget, newItem: HomeWidget): Boolean {
                val isSame: Boolean =
                    if (oldItem is HomeWidget.TitleWidget && newItem is HomeWidget.TitleWidget) {
                        oldItem.title == newItem.title
                    } else if (oldItem is HomeWidget.ChipWidget && newItem is HomeWidget.ChipWidget) {
                        oldItem.mCategories == newItem.mCategories
                    } else if (oldItem is HomeWidget.ListCategoryVideoItemWidget && newItem is HomeWidget.ListCategoryVideoItemWidget) {
                        oldItem.mUnifiedItems == newItem.mUnifiedItems
                    } else if (oldItem is HomeWidget.ListKeywordVideoItemWidget && newItem is HomeWidget.ListKeywordVideoItemWidget) {
                        oldItem.mUnifiedItems == newItem.mUnifiedItems
                    } else false
                return isSame
            }
        }

        //ViewType 정의
        private const val TYPE_TITLE = 0
        private const val TYPE_LIST_CHIP = 1
        private const val TYPE_LIST_CATEGORY_ITEM_VIDEO = 2
        private const val TYPE_LIST_KEYWORD_ITEM_VIDEO = 3
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is HomeWidget.TitleWidget -> TYPE_TITLE
            is HomeWidget.ChipWidget -> TYPE_LIST_CHIP
            is HomeWidget.ListCategoryVideoItemWidget -> TYPE_LIST_CATEGORY_ITEM_VIDEO
            is HomeWidget.ListKeywordVideoItemWidget -> TYPE_LIST_KEYWORD_ITEM_VIDEO
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_TITLE -> {
                val binding = FragmentHomeTitleBinding.inflate(inflater, parent, false)
                return TitleViewHolder(binding)
            }

            TYPE_LIST_CHIP -> {
                val binding = FragmentHomeRvChipBinding.inflate(inflater, parent, false)
                return ListChipViewHolder(binding)
            }

            TYPE_LIST_CATEGORY_ITEM_VIDEO -> {
                val binding = FragmentHomeRvItemBigBinding.inflate(inflater, parent, false)
                return ListCategoryVideoItemViewHolder(binding)
            }

            TYPE_LIST_KEYWORD_ITEM_VIDEO -> {
                val binding = FragmentHomeRvItemBinding.inflate(inflater, parent, false)
                return ListKeywordVideoItemViewHolder(binding)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }


    /**
     *  연결된 값들을 바인딩
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is HomeWidget.TitleWidget -> {
                (holder as TitleViewHolder).tvTitle.text = item.title
            }

            is HomeWidget.ChipWidget -> {
                (holder as ListChipViewHolder).apply {
                    chipListAdapter.submitList(item.mCategories)
                }
            }

            is HomeWidget.ListCategoryVideoItemWidget -> {
                (holder as ListCategoryVideoItemViewHolder).apply {
                    listCategoryVideoAdapterBig.submitList(item.mUnifiedItems)
                }
            }

            is HomeWidget.ListKeywordVideoItemWidget -> {
                (holder as ListKeywordVideoItemViewHolder).apply {
                    listKeywordVideoAdapter.submitList(item.mUnifiedItems)
                }
            }
        }
    }


    /**
     *  ViewHolder inner Class 정의
     *  해당 id을 불러 들어 변수 형태로 정의
     */
    inner class TitleViewHolder(binding: FragmentHomeTitleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvTitle = binding.tvTitle
    }

    inner class ListChipViewHolder(binding: FragmentHomeRvChipBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val rvCategory = binding.rvCategory
        val chipListAdapter = ChipListAdapter(viewModel)

        init {
            rvCategory.layoutManager =
                LinearLayoutManager(rvCategory.context, LinearLayoutManager.HORIZONTAL, false)
            rvCategory.adapter = chipListAdapter
        }
    }

    inner class ListCategoryVideoItemViewHolder(binding: FragmentHomeRvItemBigBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val rvListVideoItem = binding.rvListVideoItemBig
        val listCategoryVideoAdapterBig = ListVideoItemAdapterBig(viewModel)

        init {
            rvListVideoItem.layoutManager =
                LinearLayoutManager(rvListVideoItem.context, LinearLayoutManager.HORIZONTAL, false)
            rvListVideoItem.adapter = listCategoryVideoAdapterBig


        }
    }

    inner class ListKeywordVideoItemViewHolder(binding: FragmentHomeRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val rvListVideoItem = binding.rvListVideoItem
        val listKeywordVideoAdapter = ListVideoItemAdapter(viewModel)

        init {
            rvListVideoItem.layoutManager = GridLayoutManager(rvListVideoItem.context, 2)
            rvListVideoItem.adapter = listKeywordVideoAdapter
        }
    }
}