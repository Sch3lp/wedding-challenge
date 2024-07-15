package be.kunlabora.crafters.wedding.web.ui.components

import be.kunlabora.crafters.wedding.service.domain.Assignee
import be.kunlabora.crafters.wedding.web.ui.components.Htmx.hxPost
import kotlinx.html.FlowContent
import kotlinx.html.a
import kotlinx.html.div
import kotlinx.html.nav

object NavBar {
    fun FlowContent.navbar(selectedAssignee: Assignee?) {
        nav("navbar is-primary") {
            if (selectedAssignee != null) {
                div("navbar-end") {
                    div("navbar-item") {
                        a(classes = "button is-light") {
                            hxPost = "reset"
                            +"Hello ${selectedAssignee.name}! Not you?"
                        }
                    }
                }
            }
        }
    }
}