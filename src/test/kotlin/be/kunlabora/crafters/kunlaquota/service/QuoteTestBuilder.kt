package be.kunlabora.crafters.kunlaquota.service

import be.kunlabora.crafters.kunlaquota.service.domain.Quote
import be.kunlabora.crafters.kunlaquota.service.domain.QuoteId
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

fun aSingleLineQuote(
    quoteId: QuoteId = EntityId.new(),
    name: String = "snarf",
    text: String = "snarf snarf",
    at: LocalDateTime = LocalDateTime.now(),
) = Quote(
    id = quoteId,
    lines = listOf(Quote.Line(1, name, text)),
    at = at.truncatedTo(ChronoUnit.MICROS),
)

fun aMultiLineQuote(id: QuoteId = EntityId.new(), at: LocalDateTime = LocalDateTime.now(), build: LineBuilder.() -> Unit): Quote {
    return Quote(id = id, at = at.truncatedTo(ChronoUnit.MICROS), lines = LineBuilder().apply(build).lines)
}

class LineBuilder {
    private val linePairs: MutableList<Pair<String,String>> = mutableListOf()

    val lines: List<Quote.Line> get() = linePairs.mapIndexed { idx, (name,text) -> Quote.Line(idx+1,name,text) }

    infix fun String.said(text: String) {
        linePairs += this to text
    }
}