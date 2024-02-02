package id.rla.silungkangplayground.presentation.feature.mainpage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.rla.silungkangplayground.R
import id.rla.silungkangplayground.databinding.HeaderItemEventBinding
import id.rla.silungkangplayground.databinding.ItemEventBinding
import id.rla.silungkangplayground.domain.model.Event
import id.rla.silungkangplayground.presentation.util.loadImage

class EventAdapter:ListAdapter<Event, RecyclerView.ViewHolder>(DIFF_CALLBACK) {


    class EventViewHolder(val binding:ItemEventBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(event: Event){
            itemView.context.apply {
                binding.apply {
                    acivThumbnail.loadImage(event.thumbnailUrl)
                    actvTitle.text = event.title
                    actvDate.text = getString(R.string.tanggal_24_01_2024, event.date)
                    actvTime.text = getString(R.string.jam_event, event.time)
                }
            }
        }
    }

    class HeaderViewHolder(val binding:HeaderItemEventBinding):RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(
            parent.context
        )

        return when (viewType) {
            HEADER_TYPE -> {
               HeaderItemEventBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                ).let { HeaderViewHolder(it) }

            }
            else -> {
                ItemEventBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                ).let {  EventViewHolder(it) }

            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HeaderViewHolder) return

        val currItem = getItem(position)
        (holder as EventViewHolder).bind(currItem)
    }

    override fun getItem(position: Int): Event {
        return super.getItem(getItemFixPosition(position))
    }

    private fun getItemFixPosition(position: Int):Int{
        return position - 1
    }

    override fun getItemCount(): Int {
        return currentList.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == HEADER_POSITION){
            HEADER_TYPE
        }else CONTENT_TYPE
    }

    companion object{
        private const val HEADER_TYPE = 100
        private const val HEADER_POSITION = 0

        private const val CONTENT_TYPE = 200

        private val DIFF_CALLBACK = object:DiffUtil.ItemCallback<Event>(){
            override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
                return oldItem == newItem
            }
        }

    }
}