package com.example.natube.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.natube.databinding.VideoCategoryBinding
import com.example.natube.model.Category

class CategoryAdapter(private val viewModel: HomeViewModel) : ListAdapter<Category, RecyclerView.ViewHolder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Category>() {
            override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class CategoryViewHolder(binding: VideoCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val chip = binding.chipCategory
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            VideoCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        (holder as CategoryViewHolder).apply {
            chip.apply{
                text = item.name
                isChecked = item.isClicked

                setOnClickListener {
                    viewModel.setSelectedItemPosition(position)
                    notifyDataSetChanged()
                }
            }

        }
    }
}