package id.rla.silungkangplayground.presentation.feature.mainpage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.rla.silungkangplayground.R
import id.rla.silungkangplayground.databinding.HeaderPageItemBinding
import id.rla.silungkangplayground.databinding.ItemCardMemberBinding
import id.rla.silungkangplayground.databinding.ItemEventBinding
import id.rla.silungkangplayground.domain.model.CardMember
import id.rla.silungkangplayground.domain.model.EventPlayground
import id.rla.silungkangplayground.presentation.util.loadImage

class CardMemberAdapter(
    private val items:List<CardMember>
):RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    class ItemViewHolder(val binding:ItemCardMemberBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item:CardMember){
            binding.apply {
                acivQrCode.loadImage(item.bitmap)
                actvName.text = item.memberAccount.name
            }
        }
    }

    class HeaderViewHolder(val binding: HeaderPageItemBinding):RecyclerView.ViewHolder(binding.root){
        init {
            binding.root.text = itemView.context.getString(R.string.kartu_member)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return when(viewType){
            HEADER_TYPE -> {
                HeaderPageItemBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                ).let { HeaderViewHolder(it) }

            }
            else -> {
                ItemCardMemberBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                ).let { ItemViewHolder(it) }

            }
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HeaderViewHolder) return

        val currItem = items[getItemFixPosition(position)]
        if (holder is ItemViewHolder) holder.bind(currItem)
    }

    override fun getItemCount() = items.size + 1

    private fun getItemFixPosition(position: Int):Int{
        return position - 1
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
    }
}