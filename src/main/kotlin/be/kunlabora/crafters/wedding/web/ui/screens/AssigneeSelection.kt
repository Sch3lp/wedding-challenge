package be.kunlabora.crafters.wedding.web.ui.screens

import be.kunlabora.crafters.wedding.service.domain.Assignee
import be.kunlabora.crafters.wedding.web.ui.components.Htmx.hxPost
import be.kunlabora.crafters.wedding.web.ui.components.Htmx.hxTarget
import kotlinx.html.*

const val assigneeSelectionKey = "assignee"

fun FlowContent.showAssigneeSelection(assignees: List<Assignee>) {
    div {
        id = "selection"

        p { +"Who are you?" }
        div(classes = "select") {
            select {
                name = assigneeSelectionKey
                hxPost = "/selectassignee"
                hxTarget = "#selection"

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