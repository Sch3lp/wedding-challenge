package be.kunlabora.crafters.kunlaquota.rest

import be.kunlabora.crafters.kunlaquota.*
import be.kunlabora.crafters.kunlaquota.service.AddQuote
import be.kunlabora.crafters.kunlaquota.service.IQuotes
import be.kunlabora.crafters.kunlaquota.service.Result
import be.kunlabora.crafters.kunlaquota.service.ShareQuote
import be.kunlabora.crafters.kunlaquota.service.domain.Quote
import be.kunlabora.crafters.kunlaquota.service.domain.QuoteShare
import be.kunlabora.crafters.kunlaquota.web.WebConfig
import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.system.CapturedOutput
import org.springframework.boot.test.system.OutputCaptureExtension
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import java.time.LocalDateTime

@TestConfiguration
class FakeConfig {
    @Bean
    fun quotes(): IQuotes = FailingQuotesFake()

    class FailingQuotesFake: IQuotes {
        override fun execute(addQuote: AddQuote, dateProvider: () -> LocalDateTime): Result<AddFailure, Quote> {
            return Result.Error(QuoteAlreadyExists("💩"))
        }

        override fun execute(shareQuote: ShareQuote): Result<ShareFailure, QuoteShare> {
            return Result.Error(ShareQuoteFailed)
        }

        override fun findAll(): Result<FetchQuotesFailed, List<Quote>> {
            return Result.Error(FetchQuotesFailed)
        }

        override fun findByQuoteShare(quoteShare: QuoteShare): Result<FetchQuotesFailed, List<Quote>> {
            return Result.Error(FetchQuotesFailed)
        }
    }
}


@WebMvcTest(WebConfig::class)
@ContextConfiguration(classes = [FakeConfig::class])
@ExtendWith(OutputCaptureExtension::class)
class ApiRoutesTest(
    @Autowired private val mockMvc: MockMvc,
    @Autowired private val objectMapper: ObjectMapper,
) {

    @Test
    fun `Failures that occur when adding a quote are logged and transformed to a 500`(capturedOutput: CapturedOutput) {
        mockMvc.post("/api/quote") {
            contentType = MediaType.APPLICATION_JSON
            content = AddQuote(listOf(Quote.Line(1, "Lion-o", "STFU Snarf!"))).toJson()
        }.andExpect {
            status { isInternalServerError() }
        }

        assertThat(capturedOutput.out).contains("💩")
    }

    @Test
    fun `Failures that occur when fetching quotes are logged and transformed to a 500`(capturedOutput: CapturedOutput) {
        mockMvc.get("/api/quote") {
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isInternalServerError() }
        }

        assertThat(capturedOutput.out).contains("FetchQuotesFailed")
    }

    @Test
    fun `Failures that occur when trying to fetch a shared quote are logged and transformed to a 404`(capturedOutput: CapturedOutput) {
        mockMvc.get("/api/quote?share=GIQYTPQ") {
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isNotFound() }
        }

        assertThat(capturedOutput.out).contains("FetchQuotesFailed")
    }

    private fun Any.toJson(): String = objectMapper.writeValueAsString(this)
}