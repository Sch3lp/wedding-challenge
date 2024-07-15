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
        div("container") {
            div("box") {
                div("field") {
                    label("label") { +"Who are you?" }
                    div(classes = "select control") {
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
            }
        }
    }

    fun FlowContent.showChallenges(challenges: List<Challenge>) {
        div("container") {
            if (challenges.isEmpty()) {
                div("box") {
                    div("field") {
                        p { +"No more challenges for you! Simply enjoy the wedding!" }
                    }
                }
            } else challenges.forEach { challenge(it) }
        }
    }

    private fun FlowContent.challenge(challenge: Challenge) {
        if (!challenge.completed) uncompletedChallenge(challenge)
        else completedChallenge(challenge)
    }

    private fun FlowContent.uncompletedChallenge(challenge: Challenge) {
        div("card") {
            header("card-header") {
                p("card-header-title") { +challenge.description }
                button(classes = "card-header-icon") {
                    hxPost = "/complete/${challenge.id.value}"

                    span("icon") {
                        i("fas fa-check has-text-success")
                    }
                }
            }
        }
    }

    private fun FlowContent.completedChallenge(challenge: Challenge) {
        div("card") {
            header("card-header") {
                p("card-header-title has-text-success") { +challenge.description }
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