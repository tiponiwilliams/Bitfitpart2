package com.example.bitfitpart2.ui.entries

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bitfitpart2.data.EntryEntity
import com.example.bitfitpart2.databinding.ItemEntryBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class EntriesListAdapter :
    ListAdapter<EntryEntity, EntriesListAdapter.EntryViewHolder>(Diff) {

    object Diff : DiffUtil.ItemCallback<EntryEntity>() {
        override fun areItemsTheSame(oldItem: EntryEntity, newItem: EntryEntity) =
            oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: EntryEntity, newItem: EntryEntity) =
            oldItem == newItem
    }

    class EntryViewHolder(private val binding: ItemEntryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val sdf = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
        fun bind(item: EntryEntity) {
            binding.caloriesText.text = "${item.calories} kcal"
            binding.noteText.text = item.note ?: ""
            binding.dateText.text = sdf.format(Date(item.date))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryViewHolder {
        val binding = ItemEntryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return EntryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EntryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
