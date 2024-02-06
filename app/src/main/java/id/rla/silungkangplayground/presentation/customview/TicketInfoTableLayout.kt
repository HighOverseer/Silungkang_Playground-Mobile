package id.rla.silungkangplayground.presentation.customview

import android.content.Context
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.marginBottom
import id.rla.silungkangplayground.R
import id.rla.silungkangplayground.domain.model.TicketDayPrice
import id.rla.silungkangplayground.presentation.util.toDp

class TicketInfoTableLayout:TableLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    fun setListTicketInfoPrice(data:List<TicketDayPrice>){
        removeAllViews()

        data.forEach { ticketDayPrice ->
            val newRow = createTicketInfoPriceRow(ticketDayPrice)
            this.addView(
                newRow,
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT,
            )
        }
    }

    private fun createTicketInfoPriceRow(ticketDayPrice: TicketDayPrice):TableRow{
        val newTableRow = TableRow(context)
        newTableRow.layoutParams = TableRow.LayoutParams(
            TableRow.LayoutParams.WRAP_CONTENT,
            TableRow.LayoutParams.WRAP_CONTENT
        )

        val dayTextView = createTicketInfoTextView(
            ticketDayPrice.day
        )
        val ticketPriceDay = createTicketInfoTextView(
            context.getString(
                R.string.ticket_day_price_format,
                ticketDayPrice.price
            )
        )

        newTableRow.addView(dayTextView)
        newTableRow.addView(ticketPriceDay)

        newTableRow.setPadding(0, 0 , 0, 2.toDp(resources.displayMetrics))

        return newTableRow
    }

    private fun createTicketInfoTextView(text:String):AppCompatTextView{
        val textView = AppCompatTextView(ContextThemeWrapper(context, R.style.TextView_Purple100_11sp_Light))
        textView.layoutParams = TableRow.LayoutParams(
            TableRow.LayoutParams.WRAP_CONTENT,
            TableRow.LayoutParams.WRAP_CONTENT
        ).also { it.marginEnd = 12.toDp(resources.displayMetrics) }

        textView.text = text
        return textView
    }

}