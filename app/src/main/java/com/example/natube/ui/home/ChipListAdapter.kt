package com.example.natube.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.natube.databinding.VideoCategoryBinding
import com.example.natube.model.Chip

class ChipListAdapter(private val viewModel: HomeViewModel) : ListAdapter<Chip, RecyclerView.ViewHolder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Chip>() {
            override fun areItemsTheSame(oldItem: Chip, newItem: Chip): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Chip, newItem: Chip): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class ChipListViewHolder(binding: VideoCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val chip = binding.chipCategory
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            VideoCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChipListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        (holder as ChipListViewHolder).apply {
            chip.apply{
                text = item.name
                isChecked = item.isClicked

                setOnClickListener {
                    viewModel.setSelectedCategoryPosition(position)
                    notifyDataSetChanged()
                }
            }

        }
    }
}