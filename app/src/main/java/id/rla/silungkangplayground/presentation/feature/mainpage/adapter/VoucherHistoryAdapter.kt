package id.rla.silungkangplayground.presentation.feature.mainpage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.rla.silungkangplayground.R
import id.rla.silungkangplayground.databinding.HeaderPageItemBinding
import id.rla.silungkangplayground.databinding.HeaderTableItemVoucherHistoryBinding
import id.rla.silungkangplayground.databinding.ItemVoucherHistoryBinding
import id.rla.silungkangplayground.domain.model.VoucherHistoryItem

class VoucherHistoryAdapter :ListAdapter<VoucherHistoryItem, RecyclerView.ViewHolder>(DIFF_CALLBACK) {


    class HeaderPageViewHolder(val binding:HeaderPageItemBinding):RecyclerView.ViewHolder(binding.root){
        init {
            binding.root.text = itemView.context.getString(
                R.string.riwayat
            )
        }
    }

    class HeaderTableViewHolder(val binding:HeaderTableItemVoucherHistoryBinding):RecyclerView.ViewHolder(binding.root)

    class TableItemViewHolder(val binding:ItemVoucherHistoryBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item:VoucherHistoryItem){
            binding.apply {
                val number = adapterPosition - 1

                actvNo.text = number.toString()
                actvInformation.text = item.information
                actvDate.text = item.date
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(
            parent.context
        )

        return when(viewType){
            HEADER_PAGE_TYPE -> {
                HeaderPageItemBinding.inflate(
                    inflater,
                    parent,
                    false
                ).let { HeaderPageViewHolder(it) }
            }
            HEADER_TABLE_TYPE -> {
                HeaderTableItemVoucherHistoryBinding.inflate(
                    inflater,
                    parent,
                    false
                ).let { HeaderTableViewHolder(it) }
            }
            ITEM_TABLE_TYPE -> {
                ItemVoucherHistoryBinding.inflate(
                    inflater,
                    parent,
                    false
                ).let { TableItemViewHolder(it) }
            }
            else -> throw Exception("View Type Not found..")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HeaderTableViewHolder || holder is HeaderPageViewHolder) return

        val currItem = getItem(position)

        (holder as TableItemViewHolder).bind(currItem)
    }

    override fun getItem(position: Int): VoucherHistoryItem {
        return super.getItem(getFixItemPosition(position))
    }

    override fun getItemViewType(position: Int): Int {
        return when(position){
            HEADER_PAGE_TYPE -> HEADER_PAGE_TYPE
            HEADER_TABLE_TYPE -> HEADER_TABLE_TYPE
            else -> ITEM_TABLE_TYPE
        }
    }

    private fun getFixItemPosition(position: Int):Int{
        return position - ADDITIONAL_VIEW_COUNT
    }

    override fun getItemCount(): Int {
        return currentList.size + ADDITIONAL_VIEW_COUNT
    }


    companion object{
        private const val ADDITIONAL_VIEW_COUNT = 2

        private const val HEADER_PAGE_TYPE = 0
        private const val HEADER_TABLE_TYPE = 1
        private const val ITEM_TABLE_TYPE = 3

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<VoucherHistoryItem>(){
            override fun areItemsTheSame(
                oldItem: VoucherHistoryItem,
                newItem: VoucherHistoryItem
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: VoucherHistoryItem,
                newItem: VoucherHistoryItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}