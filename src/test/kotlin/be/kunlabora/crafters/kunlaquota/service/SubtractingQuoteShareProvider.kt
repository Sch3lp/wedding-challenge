package be.kunlabora.crafters.kunlaquota.service

import be.kunlabora.crafters.kunlaquota.service.domain.QuoteId
import be.kunlabora.crafters.kunlaquota.service.domain.QuoteShare
import be.kunlabora.crafters.kunlaquota.service.domain.QuoteShareProvider

class SubtractingQuoteShareProvider(expectedQuoteShare: QuoteShare) : QuoteShareProvider {
    private val quoteSharesToReturn = mutableListOf(
        expectedQuoteShare,
        QuoteShare("karel"),
        QuoteShare("joones"),
    )

    override operator fun invoke(quoteId: QuoteId) = quoteSharesToReturn.removeFirst()
}