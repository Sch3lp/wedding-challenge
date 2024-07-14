package be.kunlabora.crafters.kunlaquota.service.domain

import be.kunlabora.crafters.kunlaquota.service.EntityId
import java.time.LocalDateTime

typealias QuoteId = EntityId<Quote>

data class Quote(
    val id: QuoteId,
    val lines: List<Line> = emptyList(),
    val at: LocalDateTime = LocalDateTime.now(),
) {
    data class Line(
        val order: Int,
        val name: String,
        val text: String,
    )
}

@JvmInline value class QuoteShare(val value: String)