package id.rla.silungkangplayground.presentation.feature.mainpage.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class ActiveVoucherItemDecoration(
    private val marginBottomInDp:Int
):ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.bottom =  marginBottomInDp
    }

}