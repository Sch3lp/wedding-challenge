package be.kunlabora.crafters.kunlaquota.data

import be.kunlabora.crafters.kunlaquota.service.Result
import be.kunlabora.crafters.kunlaquota.service.domain.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

abstract class QuoteShareRepositoryContractTest(
    private val quoteShareRepository: QuoteShareRepository,
    private val subtractingProvider: QuoteShareProvider = HashedQuoteShareProvider()
) {

    @Test
    fun `when nothing exists for the given id and we upserting it, a new QuoteShare is created and stored`() {
        val quoteId: QuoteId = QuoteId.fromString("b9c9a2c0-577c-4e14-a20d-4771d2fcc9a8")

        assertThat(quoteShareRepository.findBy(quoteId)).isEqualTo(Result.Ok(null))

        quoteShareRepository.upsert(quoteId, subtractingProvider)

        assertThat(quoteShareRepository.findBy(quoteId)).isEqualTo(Result.Ok(QuoteShare("KCIYBUU")))
    }

    @Test
    fun `when something already exists for the given id and we upserting it, the existing QuoteShare is simply returned`() {
        val quoteId : QuoteId = QuoteId.fromString("7f5c59bf-8d08-4bf9-9a8b-c91d2a45be2b")

        quoteShareRepository.upsert(quoteId, subtractingProvider).valueOrThrow()
        assertThat(quoteShareRepository.findBy(quoteId)).isEqualTo(Result.Ok(QuoteShare("GXVWNRR")))
        assertThat(quoteShareRepository.findBy(QuoteShare("GXVWNRR"))).isEqualTo(Result.Ok(quoteId))

        quoteShareRepository.upsert(quoteId, subtractingProvider)
        assertThat(quoteShareRepository.findBy(quoteId)).isEqualTo(Result.Ok(QuoteShare("GXVWNRR")))
    }
}