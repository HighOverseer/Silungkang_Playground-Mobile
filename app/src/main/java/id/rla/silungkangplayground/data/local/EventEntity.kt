package id.rla.silungkangplayground.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("event")
data class EventEntity (

    @PrimaryKey
    @ColumnInfo("id")
    val id:String,

    @ColumnInfo("event_name")
    val eventName:String,

    @ColumnInfo("event_banner")
    val eventBanner:String,

    @ColumnInfo("event_link")
    val eventLink:String,

    @ColumnInfo("event_start")
    val eventStart:String,

    @ColumnInfo("event_end")
    val eventEnd:String

)