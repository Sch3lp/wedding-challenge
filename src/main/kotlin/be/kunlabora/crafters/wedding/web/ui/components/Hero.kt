package be.kunlabora.crafters.wedding.web.ui.components

import be.kunlabora.crafters.wedding.service.domain.Assignee
import be.kunlabora.crafters.wedding.web.ui.WeddingTheme
import be.kunlabora.crafters.wedding.web.ui.components.NavBar.navbar
import kotlinx.html.*


object Hero {
    fun FlowContent.hero(currentTheme: WeddingTheme, selectedAssigneeId: Assignee?) {
        section(classes = "hero is-primary") {
            navbar(currentTheme, selectedAssigneeId)
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