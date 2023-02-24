package uniks.cc.myfitnessapp.core.domain.util

import java.time.Instant
import java.time.ZoneId

object TimestampConverter {
    fun convertToDatetime(timestamp: Long): String {
        var dateTime = ""

        val dt = Instant.ofEpochSecond(timestamp)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()

        dt.toLocalDate()
            .toString()
            .split('-')
            .asReversed()
            .forEach { str -> dateTime += "$str." }

        dateTime = dateTime.dropLast(1)
        dateTime += ", ${dt.toLocalTime()}"
        return dateTime
    }

    fun convertToDate(timestamp: Long): String {
        return Instant.ofEpochSecond(timestamp)
            .atZone(ZoneId.systemDefault())
            .toLocalDate().toString()

    }

    fun convertToTime(timestamp: Long): String {
        return Instant.ofEpochSecond(timestamp)
            .atZone(ZoneId.systemDefault())
            .toLocalTime().toString()
    }
}