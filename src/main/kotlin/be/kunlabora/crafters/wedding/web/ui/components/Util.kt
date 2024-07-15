package be.kunlabora.crafters.wedding.web.ui.components

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Util {
    private val dateTimePattern: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    fun LocalDateTime.formatForWeb(): String = dateTimePattern.format(this)
}