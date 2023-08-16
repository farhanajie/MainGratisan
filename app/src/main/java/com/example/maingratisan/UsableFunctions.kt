package com.example.maingratisan

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun formatDate(date: String): String? {
    val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val parsedDate = LocalDateTime.parse(date, dateFormat)
    return DateTimeFormatter
        .ofPattern("dd MMMM yyyy, HH:mm")
        .format(parsedDate)
}
fun parseDateToUnix(date: String): Long {
    val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val parsedDate = LocalDateTime.parse(date, dateFormat)
    return parsedDate
        .atZone(ZoneId.systemDefault())
        .toInstant()
        .epochSecond
}