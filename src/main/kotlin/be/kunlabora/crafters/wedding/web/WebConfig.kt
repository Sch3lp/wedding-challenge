package be.kunlabora.crafters.wedding.web

import be.kunlabora.crafters.wedding.service.CompletedChallenge
import be.kunlabora.crafters.wedding.service.WeddingBehavior
import be.kunlabora.crafters.wedding.service.domain.AssigneeId
import be.kunlabora.crafters.wedding.service.domain.ChallengeId
import be.kunlabora.crafters.wedding.service.get
import be.kunlabora.crafters.wedding.web.ui.partial
import be.kunlabora.crafters.wedding.web.ui.screens.MainScreen.assigneeFieldName
import be.kunlabora.crafters.wedding.web.ui.screens.MainScreen.assigneeSelection
import be.kunlabora.crafters.wedding.web.ui.screens.MainScreen.errorMessage
import be.kunlabora.crafters.wedding.web.ui.screens.MainScreen.showChallenges
import be.kunlabora.crafters.wedding.web.ui.wrapper
import jakarta.servlet.http.Cookie
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.paramOrNull
import org.springframework.web.servlet.function.router

private const val selected_assignee_cookie = "selectedAssignee"

data class SelectedAssigneeCookie(val assigneeIdString: String) : Cookie(selected_assignee_cookie, assigneeIdString) {
    val assigneeId: AssigneeId get() = AssigneeId.fromString(assigneeIdString)
    fun deleted(): SelectedAssigneeCookie = copy(assigneeIdString = assigneeIdString)
        .apply {
            setAttribute("expires", "Thu, 01 Jan 1970 00:00:00 GMT")
        }

    init {
        path = "/"
    }
}

@Configuration
class WebConfig : WebMvcConfigurer {

    @Bean
    fun routes(wedding: WeddingBehavior) = router {
        GET("/") { request ->
            val selectedAssigneeId = request.getSelectedAssignee()?.assigneeId

            val title = "WeddingChallenge"
            ServerResponse.status(HttpStatus.OK)
                .contentType(MediaType.TEXT_HTML)
                .body(
                    wrapper(title, selectedAssigneeId) {
                        if (selectedAssigneeId != null) {
                            showChallenges(wedding.findAllChallengesFor(selectedAssigneeId))
                        } else {
                            assigneeSelection(wedding.assignees)
                        }
                    }
                )
        }

        POST("select-assignee") { request ->
            ServerResponse.status(HttpStatus.OK)
                .header("HX-Redirect", "/")
                .cookie(SelectedAssigneeCookie(request.paramOrNull(assigneeFieldName)!!))
                .build()
        }

        POST("complete/{id}") { request ->
            val id: ChallengeId = ChallengeId.fromString(request.pathVariable("id"))
            val completeChallenge = CompletedChallenge(id)
            wedding.execute(completeChallenge)
                .map {
                    ServerResponse.status(HttpStatus.OK)
                        .header("HX-Redirect", "/")
                        .build()
                }.recover { failure ->
                    ServerResponse.status(HttpStatus.OK)
                        .body(partial { errorMessage("Oopsie! Something broke!", failure.message) })
                }.get()
        }

        POST("/reset") { request ->
            request.getSelectedAssignee()
                ?.let {
                    ServerResponse.status(HttpStatus.OK)
                        .header("HX-Redirect", "/")
                        .cookie(it.deleted())
                        .build()
                } ?: ServerResponse.status(HttpStatus.OK)
                .header("HX-Redirect", "/")
                .build()
        }
    }

    private fun ServerRequest.getSelectedAssignee(): SelectedAssigneeCookie? =
        cookies().toSingleValueMap()[selected_assignee_cookie]?.let { SelectedAssigneeCookie(it.value) }
}