package be.kunlabora.crafters.wedding.web.ui.components

import be.kunlabora.crafters.wedding.service.domain.AssigneeId
import be.kunlabora.crafters.wedding.web.ui.components.NavBar.navbar
import kotlinx.html.*


object Hero {
    fun FlowContent.hero(selectedAssigneeId: AssigneeId?) {
        section(classes = "hero is-primary") {
            navbar(selectedAssigneeId)
            div(classes = "hero-body") {
                div(classes = "container") {
                    h1(classes = "title") {
                        +"Tim & Megan's Wedding"
                    }
                    h2(classes = "subtitle") {
                        +"Complete challenges as you party!"
                    }
                }
            }
        }
    }
}