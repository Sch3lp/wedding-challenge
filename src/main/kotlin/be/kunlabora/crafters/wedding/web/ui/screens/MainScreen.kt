package be.kunlabora.crafters.wedding.web.ui.screens

import be.kunlabora.crafters.wedding.service.domain.Assignee
import be.kunlabora.crafters.wedding.service.domain.Challenge
import be.kunlabora.crafters.wedding.web.ui.components.Htmx.hxPost
import be.kunlabora.crafters.wedding.web.ui.components.Util.formatForWeb
import kotlinx.html.*
import java.time.LocalDateTime

object MainScreen {
    const val assigneeFieldName = "assignee"

    fun FlowContent.assigneeSelection(assignees: List<Assignee>) {
        p { +"Who are you?" }
        div(classes = "select") {
            select {
                name = assigneeFieldName
                hxPost = "select-assignee"
                option {
                    value = ""
                    +""
                }
                assignees.forEach { assignee ->
                    option {
                        value = assignee.id.value
                        +assignee.name
                    }
                }
            }
        }
    }

    fun FlowContent.showChallenges(challenges: List<Challenge>) {
        section(classes = "section") {
            id = "quote-section"
            div(classes = "container") {
                if (challenges.isEmpty()) {
                    + "No challenges for you! Simply enjoy the wedding!"
                } else challenges.forEach { challenge(it) }
            }
        }
    }

    private fun FlowContent.challenge(challenge: Challenge) {
        div("card") {
            div("card-content") {
                + challenge.description
            }
        }
    }

    private fun FlowContent.date(at: LocalDateTime) {
        div("is-flex is-justify-content-flex-end") {
            p("is-size-7 has-text-grey-light") {
                +at.formatForWeb()
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