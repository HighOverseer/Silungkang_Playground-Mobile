package id.rla.silungkangplayground.presentation.feature.mainpage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.rla.silungkangplayground.R
import id.rla.silungkangplayground.databinding.ItemVoucherExchangeOptionBinding
import id.rla.silungkangplayground.domain.model.OfferedVoucher
import id.rla.silungkangplayground.domain.model.VoucherType

class OfferedVouchersAdapter(
    items:List<OfferedVoucher>,
    private val onItemClicked:(OfferedVoucher) -> Unit
):RecyclerView.Adapter<OfferedVouchersAdapter.VoucherExchangeOptionVH>() {

    private val listItems = items.ifEmpty {
        listOf(
            OfferedVoucher(-1, "", "", VoucherType.PLAYGROUND),
            OfferedVoucher(-1, "", "", VoucherType.CAFE)
        )
    }
    class VoucherExchangeOptionVH(
        private val binding:ItemVoucherExchangeOptionBinding,
        private val onClickedPosition:(Int) -> Unit
    ):RecyclerView.ViewHolder(binding.root){
        fun bind(item: OfferedVoucher){
            binding.apply {
                val context = itemView.context

                actvVoucherValue.text = item.value
                actvExchangePoinInfo.text = context.getString(
                    R.string.exchange_point_info_format,
                    item.type.stringValue,
                    item.costPoint
                )
            }
        }

        init {
            binding.acbExchangeButton1.setOnClickListener {
                onClickedPosition(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VoucherExchangeOptionVH {
        val binding = ItemVoucherExchangeOptionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return VoucherExchangeOptionVH(binding){ itemPosition ->
            onItemClicked(listItems[itemPosition])
        }
    }

    override fun onBindViewHolder(holder: VoucherExchangeOptionVH, position: Int) {
        val currentItem = listItems[position]
        holder.bind(currentItem)
    }

    override fun getItemCount() = listItems.size
}