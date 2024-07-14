package be.kunlabora.crafters.kunlaquota.service.domain

import be.kunlabora.crafters.kunlaquota.AddFailure
import be.kunlabora.crafters.kunlaquota.FetchQuotesFailed
import be.kunlabora.crafters.kunlaquota.service.Result

interface QuoteRepository {
    fun store(quote: Quote): Result<AddFailure, Quote>
    fun findAll(): Result<FetchQuotesFailed, List<Quote>>
}