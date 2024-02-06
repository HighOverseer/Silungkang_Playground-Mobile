package id.rla.silungkangplayground.presentation.feature.mainpage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.rla.silungkangplayground.R
import id.rla.silungkangplayground.databinding.ItemPlaygroundOperationalHoursBinding
import id.rla.silungkangplayground.databinding.ItemPlaygroundTicketInfoBinding
import id.rla.silungkangplayground.domain.model.OperationalHours
import id.rla.silungkangplayground.domain.model.PlaygroundModel
import id.rla.silungkangplayground.domain.model.TicketInfo

class PlaygroundAdapter(
    private val items:List<PlaygroundModel>
):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class OperationalHoursViewHolder(val binding:ItemPlaygroundOperationalHoursBinding):RecyclerView.ViewHolder(binding.root)

    class TicketInfoViewHolder(val binding:ItemPlaygroundTicketInfoBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(
            parent.context
        )

        return when(viewType){
            PlaygroundModel.OPERATIONAL_HOURS_TYPE_ID -> {
                ItemPlaygroundOperationalHoursBinding.inflate(
                    inflater,
                    parent,
                    false
                ).let { OperationalHoursViewHolder(it) }
            }
            PlaygroundModel.TICKET_INFO_TYPE_ID -> {
                ItemPlaygroundTicketInfoBinding.inflate(
                    inflater,
                    parent,
                    false
                ).let { TicketInfoViewHolder(it) }
            }
            else -> throw Exception("View Type Not Found...")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when(val currItem = items[position]){
            is TicketInfo -> {
                (holder as TicketInfoViewHolder).apply {
                    binding.tlTicketDayPrice.setListTicketInfoPrice(currItem.ticketDayPrices)
                    binding.actvAdditionalInfo.text = currItem.additionalInfo
                }
            }
            is OperationalHours -> {
                (holder as OperationalHoursViewHolder).apply {
                    binding.actvTime.text = itemView.context.getString(
                        R.string.operational_hours_time, currItem.openTime, currItem.closeTime
                    )
                }
            }
        }
    }



    override fun getItemViewType(position: Int): Int {
        return items[position].typeId

    }

    override fun getItemCount() = items.size
}