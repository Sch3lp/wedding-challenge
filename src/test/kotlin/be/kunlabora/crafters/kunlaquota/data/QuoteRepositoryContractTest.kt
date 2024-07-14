package be.kunlabora.crafters.kunlaquota.data

import be.kunlabora.crafters.kunlaquota.QuoteAlreadyExists
import be.kunlabora.crafters.kunlaquota.service.Result
import be.kunlabora.crafters.kunlaquota.service.aMultiLineQuote
import be.kunlabora.crafters.kunlaquota.service.aSingleLineQuote
import be.kunlabora.crafters.kunlaquota.service.domain.Quote
import be.kunlabora.crafters.kunlaquota.service.domain.QuoteId
import be.kunlabora.crafters.kunlaquota.service.domain.QuoteRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

abstract class QuoteRepositoryContractTest(private val quoteRepository: QuoteRepository) {

    @Test
    fun `can store quotes`() {
        val aQuote = aSingleLineQuote(
            name = "Jonesuuu",
            text = "When I kotlin, I kotlin. When I java, I java. But when I javascript.. I die a little on the inside.",
        )

        val storedQuote = quoteRepository.store(aQuote).valueOrThrow()

        assertThat(storedQuote).isEqualTo(aQuote)
    }

    @Test
    fun `can store multiline quotes`() {
        val aQuote = aMultiLineQuote {
            "Joker" said "Why so serious? :)"
            "Batman" said "WHERE IS SHE?!"
        }

        val storedQuote = quoteRepository.store(aQuote).valueOrThrow()

        assertThat(storedQuote).isEqualTo(aQuote)
    }

    @Test
    fun `can fetch quotes`() {
        val now = LocalDateTime.now()
        val quote1 = aSingleLineQuote(name = "Joker", text = "Why so serious? :)", at = now.minusSeconds(1)).save()
        val quote2 = aSingleLineQuote(
            name = "Uncle Ben",
            text = "With great power comes great responsibility",
            at = now
        ).save()

        val quotes = quoteRepository.findAll().valueOrThrow()

        assertThat(quotes).containsExactly(quote2, quote1)
    }

    @Test
    fun `storing an already existing quote shouldn't work`() {
        val aStoredQuote = aSingleLineQuote(name = "Joker", text = "Why so serious? :)").save()

        val actual = quoteRepository.store(aStoredQuote)

        assertThat(actual).isEqualTo(Result.Error(QuoteAlreadyExists("Quote already exists!")))
    }

    @Test
    fun `adding a multiline quote which has 2 lines that are exactly the same, should work`() {
        val lines = listOf(
            Quote.Line(1, "Lion-o", "STFU Snarf!"),
            Quote.Line(2, "Lion-o", "STFU Snarf!"),
        )
        val expect = Quote(QuoteId.new(), lines)
        val actual = quoteRepository.store(expect).valueOrThrow()

        assertThat(actual).isEqualTo(expect)
    }

    @Test
    fun `adding a multiline quote that has different order numbers than an index, should work`() {
        val lines = listOf(
            Quote.Line(3, "Lion-o", "STFU Snarf!"),
            Quote.Line(4, "Lion-o", "STFU Snarf!"),
        )
        val expect = Quote(QuoteId.new(), lines)
        val actual = quoteRepository.store(expect).valueOrThrow()

        assertThat(actual).isEqualTo(expect)
    }


    private fun Quote.save() : Quote = quoteRepository.store(this).valueOrThrow()
}