package id.rla.silungkangplayground.data.remote.dto

import com.google.gson.annotations.SerializedName

data class EventDto(

	@field:SerializedName("a_event_id")
	val eventId: String? = null,

	@field:SerializedName("a_event_end")
	val eventEnd: String? = null,

	@field:SerializedName("a_event_banner")
	val eventBanner: String? = null,

	@field:SerializedName("a_event_name")
	val eventName: String? = null,

	@field:SerializedName("a_event_link")
	val eventLink: String? = null,

	@field:SerializedName("a_event_start")
	val eventStart: String? = null
)
