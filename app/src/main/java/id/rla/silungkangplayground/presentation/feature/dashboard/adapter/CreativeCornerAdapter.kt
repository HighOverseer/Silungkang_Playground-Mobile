package id.rla.silungkangplayground.presentation.feature.dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.rla.silungkangplayground.databinding.ItemCreativeCornerDashboardBinding
import id.rla.silungkangplayground.domain.model.CreativeCorner
import id.rla.silungkangplayground.presentation.util.loadImage

class CreativeCornerAdapter(
    private val items:List<CreativeCorner>
):RecyclerView.Adapter<CreativeCornerAdapter.CreativeCornerVH>() {

    class CreativeCornerVH(val binding:ItemCreativeCornerDashboardBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(item: CreativeCorner){
            binding.apply {
                actvTitle.text = item.title
                acivIcon.loadImage(item.icon)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreativeCornerVH {
        val binding = ItemCreativeCornerDashboardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CreativeCornerVH(binding)
    }

    override fun onBindViewHolder(holder: CreativeCornerVH, position: Int) {
        val currItem = items[position]
        holder.bind(currItem)
    }

    override fun getItemCount(): Int = items.size
}