package be.kunlabora.crafters.wedding.web.ui.components

import be.kunlabora.crafters.wedding.service.domain.AssigneeId
import be.kunlabora.crafters.wedding.web.ui.components.Htmx.hxPost
import kotlinx.html.FlowContent
import kotlinx.html.a
import kotlinx.html.div
import kotlinx.html.nav

object NavBar {
    fun FlowContent.navbar(selectedAssigneeId: AssigneeId?) {
        nav("navbar is-primary") {
            if (selectedAssigneeId != null) {
                div("navbar-end") {
                    a(classes = "navbar-item") {
                        hxPost = "reset"
                        +"Log out"
                    }
                }
            }
        }
    }
}