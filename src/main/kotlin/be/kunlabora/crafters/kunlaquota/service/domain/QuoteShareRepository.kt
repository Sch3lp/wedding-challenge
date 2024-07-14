package be.kunlabora.crafters.kunlaquota.service.domain

import be.kunlabora.crafters.kunlaquota.ShareFailure
import be.kunlabora.crafters.kunlaquota.service.Result

interface QuoteShareRepository {
    fun upsert(quoteId: QuoteId, quoteShareProvider: QuoteShareProvider): Result<ShareFailure, QuoteShare>
    fun findBy(quoteId: QuoteId): Result<ShareFailure, QuoteShare?>
    fun findBy(quoteShare: QuoteShare): Result<ShareFailure, QuoteId?>
}

typealias QuoteShareProvider = (QuoteId) -> QuoteShare
