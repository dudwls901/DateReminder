package com.ongveloper.domain.model

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Serializable
data class ScheduleParams(
    @Serializable(with = LocalDateSerializer::class)
    val date: LocalDate,
    @Serializable(with = LocalTimeSerializer::class)
    val time: LocalTime,
    val where: String,
    val what: String,
) {
    @Transient
    private val nowDate = LocalDate.now()
    @Transient
    private val nowTime = LocalTime.now()

    val isRightDateTime: Boolean
        get() = (date > nowDate)  ||
            ( date == nowDate && time >= nowTime)

    val isRightContents: Boolean
        get() = where.isNotBlank() && what.isNotBlank()
}

fun ScheduleParams?.canSave(block: (ScheduleParams) -> Unit){
    if(this == null || isRightDateTime.not() || isRightContents.not()) return
    block(this)
}

fun ScheduleParams.getDateTime(): LocalDateTime = LocalDateTime.of(date,time)



object LocalDateSerializer : KSerializer<LocalDate> {
    override val descriptor = PrimitiveSerialDescriptor("LocalDate", PrimitiveKind.STRING)
    override fun deserialize(decoder: Decoder): LocalDate {
        return LocalDate.parse(decoder.decodeString(), DateTimeFormatter.ISO_LOCAL_DATE)
    }

    override fun serialize(encoder: Encoder, value: LocalDate) {
        encoder.encodeString(value.format(DateTimeFormatter.ISO_LOCAL_DATE))
    }
}

object LocalTimeSerializer : KSerializer<LocalTime> {
    override val descriptor = PrimitiveSerialDescriptor("LocalTime", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: LocalTime) {
        encoder.encodeString(value.format(DateTimeFormatter.ISO_LOCAL_TIME))
    }

    override fun deserialize(decoder: Decoder): LocalTime {
        return LocalTime.parse(decoder.decodeString(), DateTimeFormatter.ISO_LOCAL_TIME)
    }
}