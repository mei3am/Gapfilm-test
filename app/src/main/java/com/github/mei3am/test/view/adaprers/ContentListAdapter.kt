package com.github.mei3am.test.view.adaprers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.github.mei3am.test.AppExecutors
import com.github.mei3am.test.R
import com.github.mei3am.test.databinding.ContentItemBinding
import com.github.mei3am.test.models.response.Content
import com.github.mei3am.test.view.customs.DataBoundListAdapter

class ContentListAdapter (
    private val dataBindingComponent: DataBindingComponent,
    appExecutors: AppExecutors,
    private val itemClickCallback: ((Content, Boolean, Int) -> Unit),
    private val itemRemoveCallback: ((Content, Int, Boolean) -> Unit),
) : DataBoundListAdapter<Content, ContentItemBinding>(
        appExecutors = appExecutors,
        diffCallback = object : DiffUtil.ItemCallback<Content>() {
            override fun areItemsTheSame(oldItem: Content, newItem: Content): Boolean {
                return oldItem.contentId == newItem.contentId
            }

            override fun areContentsTheSame(oldItem: Content, newItem: Content): Boolean {
                return oldItem.title == newItem.title && oldItem.zoneId == newItem.zoneId
            }
        }
) {

    override fun createBinding(parent: ViewGroup, viewType: Int): ContentItemBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.content_item,
            parent,
            false,
            dataBindingComponent
        )
    }

    override fun bind(binding: ContentItemBinding, item: Content, position: Int) {
        binding.thumbImage = item.thumbImage
        binding.tvTitle.text = item.title
        binding.cbFavorite.apply {
            isChecked = item.favorite ?: false
            setOnCheckedChangeListener { _, isChecked ->
                itemRemoveCallback.invoke(item, position, isChecked)
            }
        }
        binding.tvZone.apply {
            val zoneRes = zone(item.zoneId!!)
            text = resources.getString(zoneRes)
        }
        binding.root.setOnClickListener {
            itemClickCallback.invoke(item, binding.cbFavorite.isChecked, position)
        }
    }

    private fun zone(zoneId: Int): Int = if (zoneId == 3) R.string.series else R.string.movie

}
