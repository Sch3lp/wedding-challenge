package be.kunlabora.crafters.wedding.web

import be.kunlabora.crafters.wedding.service.WeddingBehavior
import be.kunlabora.crafters.wedding.service.domain.Assignee
import be.kunlabora.crafters.wedding.service.domain.AssigneeId
import be.kunlabora.crafters.wedding.service.get
import be.kunlabora.crafters.wedding.web.ui.partial
import be.kunlabora.crafters.wedding.web.ui.screens.assigneeSelectionKey
import be.kunlabora.crafters.wedding.web.ui.screens.showAssigneeSelection
import be.kunlabora.crafters.wedding.web.ui.wrapper
import kotlinx.html.FlowContent
import kotlinx.html.p
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.paramOrNull
import org.springframework.web.servlet.function.router


@Configuration
class Routes {

    @Bean
    fun router(wedding: WeddingBehavior) = router {
        GET { request ->
            val title = "WeddingChallenge"
            val assignees = wedding.getAssignees()

            wrapperResponse(title) { showAssigneeSelection(assignees) }
        }

        POST("selectassignee") { request ->
            val assigneeIdAsString: String = request.paramOrNull(assigneeSelectionKey)!!
//            val assigneeIdAsString: String = request.pathVariable(assigneeSelectionKey)
            val assigneeId: AssigneeId = AssigneeId.fromString(assigneeIdAsString)
            wedding.getAssignee(assigneeId)
                .map { assignee ->
                    partialResponse { welcome(assignee) }
                }
                .recover { failure ->
                    partialResponse {
                        p { +failure.message }
                    }
                }
                .get()
        }
    }
}

fun FlowContent.welcome(assignee: Assignee?) {
    p { +"Hello ${assignee?.name}. Enjoy the party!" }
}

private fun partialResponse(content: FlowContent.() -> Unit) =
    ServerResponse.status(HttpStatus.OK)
        .contentType(MediaType.TEXT_HTML)
        .body(
            partial { content() }
        )

private fun wrapperResponse(title: String, content: FlowContent.() -> Unit) =
    ServerResponse.status(HttpStatus.OK)
        .contentType(MediaType.TEXT_HTML)
        .body(
            wrapper(title) { content() }
        )
