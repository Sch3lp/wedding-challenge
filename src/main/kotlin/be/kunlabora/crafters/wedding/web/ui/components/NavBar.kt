package be.kunlabora.crafters.wedding.web.ui.components

import be.kunlabora.crafters.wedding.service.domain.Assignee
import be.kunlabora.crafters.wedding.web.ui.WeddingTheme
import be.kunlabora.crafters.wedding.web.ui.components.Htmx.hxPost
import kotlinx.html.*

object NavBar {
    fun FlowContent.navbar(currentTheme: WeddingTheme, selectedAssignee: Assignee?) {
        nav("navbar is-primary") {
            div("navbar-item") {
                div("buttons") {
                    themeToggle(currentTheme)
                    logout(selectedAssignee)
                }
            }
        }
    }

    private fun FlowContent.logout(selectedAssignee: Assignee?) {
        if (selectedAssignee != null) {
            button(classes = "button") {
                hxPost = "reset"
                +"Hello ${selectedAssignee.name}! Not you?"
            }
        }
    }

    private fun FlowContent.themeToggle(currentTheme: WeddingTheme) {
        when (currentTheme) {
            WeddingTheme.Dark -> sunButton()
            WeddingTheme.Light -> moonButton()
        }
    }

    private fun FlowContent.moonButton() {
        button(classes = "button") {
            hxPost = "/toggle/${WeddingTheme.Dark}"
            span("icon has-text-link") {
                i("fas fa-moon")
            }
        }
    }

    private fun FlowContent.sunButton() {
        button(classes = "button") {
            hxPost = "/toggle/${WeddingTheme.Light}"
            span("icon has-text-warning") {
                i("fas fa-sun")
            }
        }
    }
}