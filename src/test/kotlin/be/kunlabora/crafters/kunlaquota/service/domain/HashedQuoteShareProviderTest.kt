package be.kunlabora.crafters.kunlaquota.service.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class HashedQuoteShareProviderTest {

    private val hashedQuoteShareProvider = HashedQuoteShareProvider()
    private val quoteId : QuoteId = QuoteId.fromString("f8e551f8-843c-4971-b894-cd705a96307c")

    @Test
    fun `Creates a quoteshare based on the first 8 characters of the quote id`() {
        val actual = hashedQuoteShareProvider(quoteId)

        assertThat(actual).isEqualTo(QuoteShare("NNLWCVO"))
    }
}