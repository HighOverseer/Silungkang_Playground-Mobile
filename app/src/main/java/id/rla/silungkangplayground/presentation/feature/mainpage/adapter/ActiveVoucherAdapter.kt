package id.rla.silungkangplayground.presentation.feature.mainpage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.rla.silungkangplayground.R
import id.rla.silungkangplayground.databinding.ItemVoucherBinding
import id.rla.silungkangplayground.domain.model.Voucher
import id.rla.silungkangplayground.domain.model.VoucherType

class ActiveVoucherAdapter(
    private val vouchers:List<Voucher>
) : RecyclerView.Adapter<ActiveVoucherAdapter.ActiveVoucherViewHolder>(){


    class ActiveVoucherViewHolder(val binding:ItemVoucherBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(voucher: Voucher){
            binding.apply {
                voucher.apply {
                    actvNominal.text = value
                    actvExpiredDateAndType.text = itemView.context.getString(
                        R.string.expired_date_with_voucher_type,
                        expireDate,
                        type.stringValue
                    )
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActiveVoucherViewHolder {

        val binding = ItemVoucherBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        when(viewType){
            VoucherType.CAFE.ordinal -> {
                binding.ivVoucherType.setImageResource(R.drawable.ic_donut)
            }else -> {
                binding.ivVoucherType.setImageResource(R.drawable.ic_pg)
            }
        }
        return ActiveVoucherViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ActiveVoucherViewHolder, position: Int) {
        val currItem = vouchers[position]
        holder.bind(currItem)
    }

    override fun getItemViewType(position: Int): Int {
        return vouchers[position].type.ordinal
    }

    override fun getItemCount() = vouchers.size
}