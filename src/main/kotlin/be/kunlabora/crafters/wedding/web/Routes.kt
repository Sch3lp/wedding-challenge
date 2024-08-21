package be.kunlabora.crafters.wedding.web

import be.kunlabora.crafters.wedding.service.WeddingBehavior
import be.kunlabora.crafters.wedding.service.domain.Assignee
import be.kunlabora.crafters.wedding.service.domain.AssigneeId
import be.kunlabora.crafters.wedding.web.ui.partial
import be.kunlabora.crafters.wedding.web.ui.screens.assignees
import be.kunlabora.crafters.wedding.web.ui.screens.searchEndpoint
import be.kunlabora.crafters.wedding.web.ui.screens.showAssigneeSelection
import be.kunlabora.crafters.wedding.web.ui.screens.showChallenges
import be.kunlabora.crafters.wedding.web.ui.wrapper
import jakarta.servlet.http.Cookie
import kotlinx.html.FlowContent
import kotlinx.html.p
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.paramOrNull
import org.springframework.web.servlet.function.router


@Configuration
class Routes {

    @Bean
    fun router(wedding: WeddingBehavior) = router {
        GET(searchEndpoint) { request ->
            val queryString = request.paramOrNull("q") ?: ""
            val filteredAssignees = wedding.getAssignees().filter { it.name.contains(queryString, ignoreCase = true) }

            partialResponse { assignees(filteredAssignees) }
        }

        GET { request ->
            val title = "WeddingChallenge"
            val assignees = wedding.getAssignees()

            val assignee: Assignee? = request.getSelectedAssigneeCookie()?.let {
                val assigneeIdAsString = it.value
                val assigneeId: AssigneeId = AssigneeId.fromString(assigneeIdAsString)
                wedding.getAssignee(assigneeId)
            }

            wrapperResponse(title) {
                if (assignee != null) showChallenges(wedding, assignee)
                else showAssigneeSelection(assignees)
            }
        }

        POST("select-assignee/{assigneeId}") { request ->
            val assigneeIdAsString: String = request.pathVariable("assigneeId")
            val assigneeId: AssigneeId = AssigneeId.fromString(assigneeIdAsString)
            val assignee = wedding.getAssignee(assigneeId)

            val cookie = SelectedAssigneeCookie(assignee.id.value)

            ServerResponse.status(HttpStatus.OK)
                .header("HX-Location", "/")
                .cookie(cookie)
                .build()
        }

        POST("unselect-assignee") { request ->
            val expiredCookie: SelectedAssigneeCookie? = request.getSelectedAssigneeCookie()?.expire()

            expiredCookie?.let {
                ServerResponse.status(HttpStatus.OK)
                    .header("HX-Location", "/")
                    .cookie(expiredCookie)
                    .build()
            } ?: ServerResponse.status(HttpStatus.OK)
                .header("HX-Location", "/")
                .build()
        }

        challengeRoutes(wedding)
    }

}

fun ServerRequest.getSelectedAssigneeCookie() =
    cookies()
        .getFirst("selectedAssignee")
        ?.let { SelectedAssigneeCookie(it.value) }

data class SelectedAssigneeCookie(val assigneeId: String) : Cookie("selectedAssignee", assigneeId) {
    init {
        this.path = "/"
    }

    fun expire(): SelectedAssigneeCookie = copy().apply {
        setAttribute("expires", "Thu, 01 Jan 1970 00:00:00 GMT")
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
