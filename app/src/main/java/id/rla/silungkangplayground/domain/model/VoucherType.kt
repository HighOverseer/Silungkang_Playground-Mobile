package id.rla.silungkangplayground.domain.model

enum class VoucherType(val stringValue:String, val code:String){
    CAFE("Cafe", "CF"), PLAYGROUND("Playground", "PG");

    companion object{
        val mapByCode by lazy {
            mapOf(
                CAFE.code to CAFE,
                PLAYGROUND.code to PLAYGROUND
            )
        }

    }

}