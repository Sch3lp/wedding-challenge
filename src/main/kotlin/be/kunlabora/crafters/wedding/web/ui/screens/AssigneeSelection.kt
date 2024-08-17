package be.kunlabora.crafters.wedding.web.ui.screens

import be.kunlabora.crafters.wedding.service.domain.Assignee
import be.kunlabora.crafters.wedding.web.ui.components.Htmx.hxGet
import be.kunlabora.crafters.wedding.web.ui.components.Htmx.hxPost
import be.kunlabora.crafters.wedding.web.ui.components.Htmx.hxTarget
import be.kunlabora.crafters.wedding.web.ui.components.Htmx.hxTrigger
import be.kunlabora.crafters.wedding.web.ui.components.Htmx.hyper
import kotlinx.html.*

const val searchEndpoint = "search"

private const val assigneeDropdownId = "assigneeDropdown"
fun FlowContent.showAssigneeSelection(assignees: List<Assignee>) {
    div("container") {
        div("dropdown") {
            id = assigneeDropdownId
            div("dropdown-trigger") {
                assigneeSearch()
            }
            assignees(assignees)
        }
    }
}

private fun DIV.assigneeSearch() {
    div("control has-icons-left") {
        searchInput(classes = "input is-medium") {
            placeholder = "Who are you?"
            name = "q"
            hxGet = searchEndpoint
            hxTrigger = "input changed delay:250ms, search"
            hxTarget = "#$assigneesId"

            hyper = "on htmx:afterOnLoad wait 10ms then add .is-active to #$assigneeDropdownId"

            autoComplete = false
            attributes["aria-haspopup"] = "true"
            attributes["aria-controls"] = "dropdown-menu"
        }
        span("icon is-left") {
            i("fa fa-search")
        }
    }
}

private const val assigneesId = "assignees"
fun FlowContent.assignees(assignees: List<Assignee>) {
    div("dropdown-menu") {
        id = assigneesId
        role = "menu"
        div("dropdown-content is-large") {
            assignees.forEach { assignee ->
                a(classes = "dropdown-item") {
                    hxPost = "select-assignee/${assignee.id.value}"
                    hxTarget = "#$assigneeDropdownId"
                    +assignee.name
                }
            }
        }
    }
}
