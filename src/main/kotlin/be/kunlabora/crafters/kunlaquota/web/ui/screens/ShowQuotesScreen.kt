package be.kunlabora.crafters.kunlaquota.web.ui.screens

import be.kunlabora.crafters.kunlaquota.service.domain.Quote
import be.kunlabora.crafters.kunlaquota.web.ui.baseUiUrl
import be.kunlabora.crafters.kunlaquota.web.ui.components.Htmx.hxGet
import be.kunlabora.crafters.kunlaquota.web.ui.components.Htmx.hxTarget
import be.kunlabora.crafters.kunlaquota.web.ui.components.Htmx.hyper
import be.kunlabora.crafters.kunlaquota.web.ui.components.Util.formatToKunlaDate
import be.kunlabora.crafters.kunlaquota.web.ui.path
import be.kunlabora.crafters.kunlaquota.web.ui.screens.AddQuoteModal.addQuoteModalId
import kotlinx.html.*
import java.time.LocalDateTime

object ShowQuotesScreen {
    fun FlowContent.showQuotes(allQuotes: List<Quote>) {
        section(classes = "section") {
            id = "quote-section"
            div(classes = "container") {
                if (allQuotes.isEmpty()) {
                    + "Be the first to create a new quote!"
                }else allQuotes.forEach { quote(it) }
            }
        }

        button(classes = "button is-primary") {
            id = "add-quote"
            style = "position: fixed; bottom: 20px; right: 20px;"
            hxGet = baseUiUrl.path("showModal")
            hxTarget = "#modals-here"
            hyper = "on htmx:afterOnLoad wait 10ms then add .is-active to $addQuoteModalId"
            +"Add Quote"
        }
    }

    private fun FlowContent.quote(quote: Quote) {
        div("card") {
            div("card-content") {
                quote.lines.forEach { quoteLine(it); br }
                p(classes = "subtitle") {
                    quoteDate(quote.at)
                }
            }
        }
    }

    private fun FlowContent.quoteLine(line: Quote.Line) {
        p {
            strong { +"${line.name}: " }
            +line.text
        }
    }

    private fun FlowContent.quoteDate(at: LocalDateTime) {
        div("is-flex is-justify-content-flex-end") {
            p("is-size-7 has-text-grey-light") {
                +at.formatToKunlaDate()
            }
        }
    }

    fun FlowContent.errorMessage(vararg messages: String) {
        article("message is-danger") {
            div(classes = "message-body") {
                messages.forEach { message ->
                    p { +message }
                }
            }
        }
    }
}