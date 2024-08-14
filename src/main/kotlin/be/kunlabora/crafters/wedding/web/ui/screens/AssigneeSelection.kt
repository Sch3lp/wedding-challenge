package be.kunlabora.crafters.wedding.web.ui.screens

import be.kunlabora.crafters.wedding.service.domain.Assignee
import be.kunlabora.crafters.wedding.web.ui.components.Htmx.hxGet
import be.kunlabora.crafters.wedding.web.ui.components.Htmx.hxPost
import be.kunlabora.crafters.wedding.web.ui.components.Htmx.hxTarget
import be.kunlabora.crafters.wedding.web.ui.components.Htmx.hxTrigger
import be.kunlabora.crafters.wedding.web.ui.components.Htmx.hyper
import kotlinx.html.*

const val assigneeSelectionKey = "assignee"

@HtmlTagMarker
inline fun FlowContent.div(classes : String? = null, crossinline block : DIV.() -> Unit = {}) : Unit =
    DIV(attributesMapOf("class", classes), consumer).visit(block)

@Suppress("unused")
open class DIV(
    initialAttributes: Map<String, String>,
    override val consumer: TagConsumer<*>
) : HTMLTag(
    tagName = "div",
    consumer = consumer,
    initialAttributes = initialAttributes,
    namespace = null,
    inlineTag = false,
    emptyTag = false
), HtmlBlockTag

inline fun FlowContent.assigneeSelection(crossinline block: DIV.() -> Unit) =
    DIV(attributesMapOf("class", "container"), consumer).visit(block)

const val searchEndpoint = "search"
fun FlowContent.showAssigneeSelection(assignees: List<Assignee>) {
    assigneeSelection {
        div("dropdown") {
            id = "assigneeDropdown"
            div("dropdown-trigger") {
                div("control has-icons-left") {
                    input(
                        type = InputType.search,
                        classes = "input is-medium"
                    ) {
                        placeholder = "Who are you?"
                        name = "q"
                        hxGet = searchEndpoint
                        hxTrigger = "input changed delay:250ms, search"
                        hxTarget = "#assignees"

                        hyper = "on htmx:afterOnLoad wait 10ms then add .is-active to #assigneeDropdown"

                        autoComplete = false
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
                    hxTarget = "#assigneeDropdown"
                    +assignee.name
                }
            }
        }
    }
}
