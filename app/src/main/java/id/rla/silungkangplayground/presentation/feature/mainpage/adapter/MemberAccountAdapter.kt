package id.rla.silungkangplayground.presentation.feature.mainpage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.rla.silungkangplayground.databinding.ItemMainpageAccountBinding
import id.rla.silungkangplayground.domain.model.MemberAccount

class MemberAccountAdapter(
    private val items:List<MemberAccount>,
    private val onClickItem: (String) -> Unit,
):RecyclerView.Adapter<MemberAccountAdapter.MemberAccountViewHolder>() {

    class MemberAccountViewHolder(
        val binding:ItemMainpageAccountBinding,
    ):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberAccountViewHolder {
        val binding = ItemMainpageAccountBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MemberAccountViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MemberAccountViewHolder, position: Int) {
        val currItem = items[position]
        holder.binding.root.text = currItem.name
        holder.itemView.setOnClickListener {
            onClickItem(currItem.id)
        }
    }

    override fun getItemCount() = items.size
}