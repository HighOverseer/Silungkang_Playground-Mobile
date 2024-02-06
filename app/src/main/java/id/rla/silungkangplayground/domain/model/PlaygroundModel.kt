package id.rla.silungkangplayground.domain.model

sealed class PlaygroundModel(val typeId:Int){
    companion object{
        const val OPERATIONAL_HOURS_TYPE_ID = 1
        const val TICKET_INFO_TYPE_ID  = 2
    }
}

data class OperationalHours(
    val openTime:String,
    val closeTime:String
):PlaygroundModel(OPERATIONAL_HOURS_TYPE_ID)

data class TicketInfo(
    val additionalInfo:String,
    val ticketDayPrices: List<TicketDayPrice>
):PlaygroundModel(TICKET_INFO_TYPE_ID)

data class TicketDayPrice(
    val day:String,
    val price:String
)




