package be.kunlabora.crafters.wedding.web.ui.components

import be.kunlabora.crafters.wedding.service.domain.Assignee
import be.kunlabora.crafters.wedding.web.ui.WeddingTheme
import be.kunlabora.crafters.wedding.web.ui.components.Htmx.hxPost
import kotlinx.html.*

object NavBar {
    fun FlowContent.navbar(currentTheme: WeddingTheme, selectedAssignee: Assignee?) {
        nav("navbar is-primary") {
            themeToggle(currentTheme)
            logout(selectedAssignee)
        }
    }

    private fun NAV.logout(selectedAssignee: Assignee?) {
        if (selectedAssignee != null) {
            div("navbar-item") {
                a(classes = "button is-link") {
                    hxPost = "reset"
                    +"Hello ${selectedAssignee.name}! Not you?"
                }
            }
        }
    }

    private fun FlowContent.themeToggle(currentTheme: WeddingTheme) {
        div("navbar-item") {
            when (currentTheme) {
                WeddingTheme.Dark -> sunButton()
                WeddingTheme.Light -> moonButton()
            }
        }
    }

    private fun DIV.moonButton() {
        button(classes = "button") {
            hxPost = "/toggle/${WeddingTheme.Dark}"
            span("icon has-text-link") {
                i("fas fa-moon")
            }
        }
    }

    private fun DIV.sunButton() {
        button(classes = "button") {
            hxPost = "/toggle/${WeddingTheme.Light}"
            span("icon has-text-warning") {
                i("fas fa-sun")
            }
        }
    }
}