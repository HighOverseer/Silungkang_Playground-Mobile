package id.rla.silungkangplayground.presentation.feature.dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.rla.silungkangplayground.databinding.ItemSliderEventDashboardBinding
import id.rla.silungkangplayground.presentation.util.loadImage

class DashboardEventSliderAdapter(
    private val items:List<String>,
):RecyclerView.Adapter<DashboardEventSliderAdapter.DashboardEventSliderVH>() {

    class DashboardEventSliderVH(
        val binding:ItemSliderEventDashboardBinding
    ) :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DashboardEventSliderVH {
        val binding = ItemSliderEventDashboardBinding.inflate(
            LayoutInflater.from(
                parent.context
            ),
            parent,
            false
        )
        return DashboardEventSliderVH(binding)
    }

    override fun onBindViewHolder(
        holder: DashboardEventSliderVH,
        position: Int
    ) {
        val currItem = items[position]
        holder.binding.ivSliderThumbnail.loadImage(currItem)
    }

    override fun getItemCount() = items.size
}