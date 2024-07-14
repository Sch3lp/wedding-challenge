package be.kunlabora.crafters.kunlaquota.web.ui.components

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Util {
    private val dateTimePattern: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    fun LocalDateTime.formatToKunlaDate(): String = dateTimePattern.format(this)
}