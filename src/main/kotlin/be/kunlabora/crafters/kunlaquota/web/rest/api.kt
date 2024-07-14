package be.kunlabora.crafters.kunlaquota.web.rest

import be.kunlabora.crafters.kunlaquota.AddFailure
import be.kunlabora.crafters.kunlaquota.QuoteAlreadyExists
import be.kunlabora.crafters.kunlaquota.QuoteIsInvalid
import be.kunlabora.crafters.kunlaquota.ShareQuoteFailed
import be.kunlabora.crafters.kunlaquota.service.*
import be.kunlabora.crafters.kunlaquota.service.domain.QuoteShare
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.web.servlet.function.*
import java.net.URI

fun apiRoutes(quotes: IQuotes): RouterFunctionDsl.() -> Unit = {
    fun AddQuote.execute() = quotes.execute(this)
    fun ShareQuote.execute() = quotes.execute(this)

    "/quote".nest {
        GET(RequestPredicates.param("share") { true }) { request ->
            quotes.findByQuoteShare(QuoteShare(request.paramOrNull("share")!!))
                .map { foundQuotes ->
                    ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(foundQuotes)
                }
                .recover { failure ->
                    ServerResponse.status(404).build().also { logger.error("$failure") }
                }.get()
        }

        GET("") {
            quotes.findAll()
                .map { foundQuotes ->
                    ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(foundQuotes)
                }
                .recover { failure ->
                    ServerResponse.status(500).build().also { logger.error("$failure") }
                }.get()
        }

        POST("") { request ->
            request.body<AddQuote>()
                .execute()
                .map { quote ->
                    ServerResponse.created(quote.id.asUri(request)).build()
                }
                .handleAddQuoteFailure()
                .get()
        }

        POST("share") { request ->
            request.body<ShareQuote>()
                .execute()
                .map { quoteShare ->
                    ServerResponse.ok().body(quoteShare.asUri(request).toString())
                }
                .recover { failure ->
                    when (failure) {
                        is ShareQuoteFailed -> ServerResponse.status(500).build().also { logger.error(failure.message) }
                    }
                }
                .get()
        }
    }
}

private fun Result<AddFailure, ServerResponse>.handleAddQuoteFailure() =
    recover { failure ->
        when (failure) {
            is QuoteIsInvalid -> ServerResponse.status(400).body(AddQuoteError(failure.message))
            is QuoteAlreadyExists -> ServerResponse.status(500)
                .body(AddQuoteError("Something fishy occurred on our side. Maybe try again?"))
        }.also { logger.error(failure.message) }
    }


private val logger = LoggerFactory.getLogger("KunlaQuotaLogger")

private fun <E> EntityId<E>.asUri(request: ServerRequest): URI =
    request.uriBuilder().path("/{id}").build(this.value)

private fun QuoteShare.asUri(request: ServerRequest): URI =
    request.uriBuilder().replacePath("/api/quote").queryParam("share",this.value).build()