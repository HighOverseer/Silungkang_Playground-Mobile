package id.rla.silungkangplayground.presentation.feature.mainpage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.rla.silungkangplayground.R
import id.rla.silungkangplayground.databinding.HeaderPageItemBinding
import id.rla.silungkangplayground.databinding.ItemEventBinding
import id.rla.silungkangplayground.domain.model.EventPlayground
import id.rla.silungkangplayground.presentation.util.loadImage

class EventPlaygroundPagingAdapter
    :PagingDataAdapter
    <EventPlayground, EventPlaygroundPagingAdapter.EventViewHolder>
    (DIFF_CALLBACK) {

    class EventViewHolder(val binding: ItemEventBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(eventPlayground: EventPlayground){
            itemView.context.apply {
                binding.apply {
                    acivThumbnail.loadImage(eventPlayground.thumbnailUrl)
                    actvTitle.text = eventPlayground.title
                    actvDate.text = getString(R.string.tanggal_awal, eventPlayground.dateStart)
                    actvTime.text = getString(R.string.tanggal_berakhir, eventPlayground.dateFinish)
                }
            }
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):EventViewHolder {
        val layoutInflater = LayoutInflater.from(
            parent.context
        )

        return ItemEventBinding.inflate(
            layoutInflater,
            parent,
            false
        ).let { EventViewHolder(it) }
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null){
            holder.bind(data)
        }
    }


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<EventPlayground>() {
            override fun areItemsTheSame(
                oldItem: EventPlayground,
                newItem: EventPlayground
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: EventPlayground,
                newItem: EventPlayground
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
