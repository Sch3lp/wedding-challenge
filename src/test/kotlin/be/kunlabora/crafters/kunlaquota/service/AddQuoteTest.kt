package be.kunlabora.crafters.kunlaquota.service

import be.kunlabora.crafters.kunlaquota.QuoteIsInvalid
import be.kunlabora.crafters.kunlaquota.service.Result.Error
import be.kunlabora.crafters.kunlaquota.service.domain.Quote
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class AddQuoteTest {
    @Test
    fun `AddQuote does not validate when one of the lines doesn't contain a name`() {
        val result = AddQuote(
            lines = listOf(
                Quote.Line(1, name = "Snarf", text = "Snarf snarf"),
                Quote.Line(2, name = "", text = "Snarf snarf"),
                Quote.Line(3, name = "Snarf", text = "Snarf snarf"),
            )
        ).validate()

        assertThat(result).isEqualTo(Error(QuoteIsInvalid("A Quote Line needs a name.")))
    }

    @Test
    fun `AddQuote does not validate when one of the lines doesn't contain a text`() {
        val result = AddQuote(
            lines = listOf(
                Quote.Line(1, name = "Snarf", text = "Snarf snarf"),
                Quote.Line(2, name = "Snarf", text = ""),
                Quote.Line(3, name = "Snarf", text = "Snarf snarf"),
            )
        ).validate()

        assertThat(result).isEqualTo(Error(QuoteIsInvalid("A Quote Line needs text.")))
    }

    @Test
    fun `AddQuote does not validate when the lines' orders are not unique`() {
        val result = AddQuote(
            lines = listOf(
                Quote.Line(1, name = "Snarf", text = "Snarf snarf"),
                Quote.Line(1, name = "Snarf", text = "Snarf snarf"),
            )
        ).validate()

        assertThat(result).isEqualTo(Error(QuoteIsInvalid("Can't have multiple lines with the same order.")))
    }

    @Test
    fun `AddQuote does not validate when all of the lines are blank`() {
        val result = AddQuote(
            lines = listOf(
                Quote.Line(1, name = "", text = ""),
            )
        ).validate()

        assertThat(result).isEqualTo(Error(QuoteIsInvalid("A Quote needs at least one Line.")))
    }

    @Test
    fun `AddQuote validation ignores completely blank lines and cleans it`() {
        val result = AddQuote(
            lines = listOf(
                Quote.Line(1, name = "Snarf", text = "Snarf snarf"),
                Quote.Line(2, name = "", text = ""),
                Quote.Line(3, name = "Snarf", text = "Snarf snarf"),
            )
        ).validate()

        assertThat(result).isEqualTo(Result.Ok(
            AddQuote(
                lines = listOf(
                    Quote.Line(1, name = "Snarf", text = "Snarf snarf"),
                    Quote.Line(2, name = "Snarf", text = "Snarf snarf"),
                )
            )
        ))
    }


}