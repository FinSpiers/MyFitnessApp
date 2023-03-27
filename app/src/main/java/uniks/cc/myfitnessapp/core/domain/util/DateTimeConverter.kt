package uniks.cc.myfitnessapp.core.domain.util

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

object TimestampConverter {

    fun convertToLocalDateTime(timestamp: Long) : LocalDateTime {
        return Instant.ofEpochSecond(timestamp)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()
    }

    fun convertToDatetime(timestamp: Long): String {
        val dt = Instant.ofEpochSecond(timestamp)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()
        return "${dt.dayOfMonth}.${dt.monthValue}.${dt.year}, ${dt.hour}:${dt.minute}"
    }

    fun convertToDate(timestamp: Long): String {
        val dt = Instant.ofEpochSecond(timestamp)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
        return "${dt.dayOfMonth}.${dt.monthValue}.${dt.year}"
    }

    fun convertToTime(timestamp: Long): String {
        return Instant.ofEpochSecond(timestamp)
            .atZone(ZoneId.systemDefault())
            .toLocalTime().toString().dropLast(3)
    }
}