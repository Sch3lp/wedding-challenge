package be.kunlabora.crafters.wedding.web.ui.screens

import be.kunlabora.crafters.wedding.service.domain.Assignee
import be.kunlabora.crafters.wedding.service.domain.Challenge
import be.kunlabora.crafters.wedding.web.ui.components.Htmx.hxGet
import be.kunlabora.crafters.wedding.web.ui.components.Htmx.hxPost
import be.kunlabora.crafters.wedding.web.ui.components.Htmx.hxTarget
import be.kunlabora.crafters.wedding.web.ui.components.Htmx.hxTrigger
import be.kunlabora.crafters.wedding.web.ui.components.Htmx.hyper
import be.kunlabora.crafters.wedding.web.ui.components.Util.formatForWeb
import kotlinx.html.*
import java.time.LocalDateTime

object MainScreen {
    const val assigneeDropdown = "assignee-dropdown"

    fun FlowContent.assigneeSelection(assignees: List<Assignee>) {
        div("container") {
            div("dropdown") {
                id = assigneeDropdown
                div("dropdown-trigger") {
                    div("control has-icons-left") {
                        input(
                            type = InputType.search,
                            classes = "input is-medium"
                        ) {
                            placeholder = "Who are you?"
                            name = "q"
                            hxGet = "search"
                            hxTrigger = "input changed delay:500ms, search"
                            hxTarget = "#assignees"

                            hyper = "on htmx:afterOnLoad wait 10ms then add .is-active to #$assigneeDropdown"

                            attributes["aria-haspopup"] = "true"
                            attributes["aria-controls"] = "dropdown-menu"
                        }
                        span("icon is-left") {
                            i("fa fa-search")
                        }
                    }
                }
                assignees(assignees)
            }
        }
    }

    fun FlowContent.assignees(assignees: List<Assignee>) {
        div("dropdown-menu") {
            id = "assignees"
            role = "menu"
            div("dropdown-content is-large") {
                assignees.forEach { assignee ->
                    a(classes = "dropdown-item") {
                        hxPost = "select-assignee/${assignee.id.value}"
                        +assignee.name
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
                        p { +"No challenges for you! Simply enjoy the wedding!" }
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