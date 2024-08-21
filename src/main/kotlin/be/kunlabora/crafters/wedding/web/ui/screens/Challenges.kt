package be.kunlabora.crafters.wedding.web.ui.screens

import be.kunlabora.crafters.wedding.service.WeddingBehavior
import be.kunlabora.crafters.wedding.service.domain.Assignee
import be.kunlabora.crafters.wedding.service.domain.Challenge
import be.kunlabora.crafters.wedding.web.ui.components.Htmx.hxPost
import kotlinx.html.*

fun FlowContent.showChallenges(wedding: WeddingBehavior, selectedAssignee: Assignee) {
    val assigneeChallenges = wedding.findAllChallengesFor(selectedAssignee.id)
    div("container") {

        button(classes = "button is-primary") {
            hxPost = "unselect-assignee"
            +"Not you?"
        }

        if (assigneeChallenges.isEmpty()) emptyChallenges()
        else {
            assigneeChallenges(assigneeChallenges)

            if (assigneeChallenges.all { it.completed }) {
                showAllCompletedChallenges(wedding.allCompletedChallenges(selectedAssignee.id))
            }
        }
    }
}

private fun DIV.assigneeChallenges(challenges: List<Challenge>) {
    challenges.forEach { challenge(it) }
}

private fun DIV.emptyChallenges() {
    div("box") {
        div("field") {
            p { +"No challenges for you! Simply enjoy the wedding!" }
        }
    }
}

private fun FlowContent.showAllCompletedChallenges(completedChallenges: List<Pair<String, String>>) {
    if (completedChallenges.isNotEmpty()) {
        p(classes = "ml-3") { +"Other people completed these already:" }
        completedChallenges.forEach { completedChallenge(it.second, it.first) }
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
            button(classes = "card-header-icon is-primary") {
                hxPost = "/complete/${challenge.id.value}"
                +"Complete"
                span("icon") {
                    i("fas fa-check has-text-success")
                }
            }
        }
    }
}

private fun FlowContent.completedChallenge(challengeDescription: String, assigneeName: String) {
    div("card") {
        header("card-header") {
            p("card-header-title has-text-success") {
                +"$assigneeName completed: $challengeDescription"
            }
        }
    }
}

private fun FlowContent.completedChallenge(challenge: Challenge) {
    div("card") {
        header("card-header") {
            p("card-header-title has-text-success") { +challenge.description }
            button(classes = "card-header-icon is-primary") {
                hxPost = "/uncomplete/${challenge.id.value}"
                +"Undo"
                span("icon") {
                    i("fas fa-undo has-text-warning")
                }
            }
        }
    }
}