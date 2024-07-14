package be.kunlabora.crafters.kunlaquota.service.domain

import be.kunlabora.crafters.kunlaquota.ShareFailure
import be.kunlabora.crafters.kunlaquota.service.Result

class QuoteShareRepositoryFake: QuoteShareRepository {
    private val quoteShares: MutableMap<QuoteId, QuoteShare> = mutableMapOf()

    override fun upsert(quoteId: QuoteId, quoteShareProvider: QuoteShareProvider): Result<ShareFailure, QuoteShare> {
        return quoteShares[quoteId]
            ?.let { Result.Ok(it) }
            ?: quoteShareProvider(quoteId)
                .also { quoteShares[quoteId] = it }
                .let { Result.Ok(it) }
    }

    override fun findBy(quoteId: QuoteId): Result<ShareFailure, QuoteShare?> {
        return Result.Ok(quoteShares[quoteId])
    }

    override fun findBy(quoteShare: QuoteShare): Result<ShareFailure, QuoteId?> {
        return Result.Ok(quoteShares.filterValues { it == quoteShare }.keys.firstOrNull())
    }
}