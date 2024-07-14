package be.kunlabora.crafters.kunlaquota.web.ui.screens

import be.kunlabora.crafters.kunlaquota.web.ui.baseUiUrl
import be.kunlabora.crafters.kunlaquota.web.ui.components.Htmx.hxPost
import be.kunlabora.crafters.kunlaquota.web.ui.components.Htmx.hxSwap
import be.kunlabora.crafters.kunlaquota.web.ui.components.Htmx.hxTarget
import be.kunlabora.crafters.kunlaquota.web.ui.components.Htmx.hyper
import be.kunlabora.crafters.kunlaquota.web.ui.path
import kotlinx.html.*

object AddQuoteModal {
    const val addQuoteModalId = "addquotemodal"

    fun FlowContent.addQuoteModal() {
        div(classes = "modal") {
            id = addQuoteModalId
            div(classes = "modal-background")
            div(classes = "modal-card") {
                style = "border-radius: 8px;" // Ensure consistent rounded corners
                header()
                form()
            }
        }
    }

    private fun DIV.form() {
        form {
            hxTarget = "#errorMessages"
            hxSwap = "innerHTML"
            hxPost = baseUiUrl.path("new")
            hyper = "on submit take .is-active from $addQuoteModalId"

            section(classes = "modal-card-body") {
                div("field") {
                    id = "extraLine"
                    addQuoteLine()
                }

                div("control has-text-centered") {
                    addExtraLineButton()
                }
            }
            footer()
        }
    }

    private fun DIV.addExtraLineButton() {
        button(classes = "button is-link is-light is-large") {
            type = ButtonType.button
            hxPost = baseUiUrl.path("new", "addLine")
            hxTarget = "#extraLine"
            hxSwap = "beforeend"
            span(classes = "icon") {
                i(classes = "fas fa-plus")
            }
        }
    }

    private fun FORM.footer() {
        footer(classes = "modal-card-foot is-justify-content-end") {
            style = "border-top: none; display: flex; justify-content: flex-end; width: 100%;"
            submitButton()
            cancelButton()
        }
    }

    private fun FOOTER.submitButton() {
        button(classes = "button is-primary is-success") {
            type = ButtonType.submit
            style = "margin-right: 0.5rem;"
            +"Save"
        }
    }

    private val removeModalHypertext = "on click take .is-active from $addQuoteModalId wait 200ms then remove $addQuoteModalId"
    private fun FOOTER.cancelButton() {
        button(classes = "button") {
            type = ButtonType.button
            hyper = removeModalHypertext
            +"Cancel"
        }
    }

    private fun HEADER.closeButton() {
        button(classes = "delete") {
            id = "delete"
            attributes["aria-label"] = "close"
            hyper = removeModalHypertext
        }
    }

    private fun DIV.header() {
        header(classes = "modal-card-head") {
            style = "border-bottom: none;" // Remove the gradient
            p(classes = "modal-card-title") { +"Add a Quote" }
            closeButton()
        }
    }

    fun FlowContent.addQuoteLine() {
        div("field is-grouped") {
            div("control") {
                input(InputType.text, classes = "input", name = "nameInput") {
                    placeholder = "Name"
                }
            }
            div("control is-expanded") {
                input(InputType.text, classes = "input", name = "textInput") {
                    placeholder = "Text"
                }
            }
        }
    }
}