package id.rla.silungkangplayground.presentation.customview

import android.content.Context
import android.util.AttributeSet
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import id.rla.silungkangplayground.presentation.feature.dashboard.adapter.DashboardEventSliderAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RecyclerSliderView:RecyclerView {

    constructor(context: Context) : super(context){
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs){
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ){
        init()
    }




    private fun init(){
        LinearSnapHelper().apply {
            attachToRecyclerView(this@RecyclerSliderView)
        }
    }

    fun setSlideAutomatically(lifecycleOwner: LifecycleOwner){
        lifecycleOwner.lifecycleScope.launch {
            lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                var isSlideDirectionClockwise = true
                while (true){
                    val adapter = adapter ?: return@repeatOnLifecycle
                    val layoutManager = layoutManager ?: return@repeatOnLifecycle
                    val itemLastPosition = adapter.itemCount - 1
                    if (adapter !is DashboardEventSliderAdapter || layoutManager !is LinearLayoutManager) {
                        delay(SLIDE_INTERVAL)
                        continue
                    }

                    if (isSlideDirectionClockwise){
                        if (layoutManager.findFirstCompletelyVisibleItemPosition() < itemLastPosition){
                            scrollToNextItem()
                        }else if(layoutManager.findFirstCompletelyVisibleItemPosition() == itemLastPosition){
                            scrollToPreviousItem()
                            isSlideDirectionClockwise = false
                        }
                    }else{
                        if (layoutManager.findFirstCompletelyVisibleItemPosition() > 0){
                            scrollToPreviousItem()
                        }else if(layoutManager.findFirstCompletelyVisibleItemPosition() == 0){
                            scrollToNextItem()
                            isSlideDirectionClockwise = true
                        }
                    }

                    delay(SLIDE_INTERVAL)
                }
            }
        }
    }

    fun scrollToNextItem(){
        if (layoutManager !is LinearLayoutManager) return

        smoothScrollToPosition(
            (layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition() + 1
        )
    }

    fun scrollToPreviousItem(){
        if (layoutManager !is LinearLayoutManager) return

        smoothScrollToPosition(
            (layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition() - 1
        )
    }

    companion object{
        const val SLIDE_INTERVAL = 7000L
    }

}