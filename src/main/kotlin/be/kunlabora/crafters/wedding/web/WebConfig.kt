package be.kunlabora.crafters.wedding.web

import be.kunlabora.crafters.wedding.service.CompleteChallenge
import be.kunlabora.crafters.wedding.service.UndoCompletedChallenge
import be.kunlabora.crafters.wedding.service.WeddingBehavior
import be.kunlabora.crafters.wedding.service.domain.Assignee
import be.kunlabora.crafters.wedding.service.domain.AssigneeId
import be.kunlabora.crafters.wedding.service.domain.ChallengeId
import be.kunlabora.crafters.wedding.service.get
import be.kunlabora.crafters.wedding.web.ui.WeddingTheme
import be.kunlabora.crafters.wedding.web.ui.partial
import be.kunlabora.crafters.wedding.web.ui.screens.MainScreen.assigneeSelection
import be.kunlabora.crafters.wedding.web.ui.screens.MainScreen.assignees
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

@Configuration
class WebConfig : WebMvcConfigurer {

    @Bean
    fun routes(wedding: WeddingBehavior) = router {
        fun SelectedAssigneeCookie?.fetch(): Assignee? =
            this?.assigneeId?.let { id -> wedding.assignees.first { it.id == id } }

        GET("/") { request ->
            val selectedAssignee = request.getSelectedAssignee()?.fetch()
            val theme: WeddingTheme? = request.getTheme()

            val title = "WeddingChallenge"
            ServerResponse.status(HttpStatus.OK)
                .contentType(MediaType.TEXT_HTML)
                .apply { if (theme == null) cookie(WeddingThemeCookie(WeddingTheme.default)) }
                .body(
                    wrapper(title, theme ?: WeddingTheme.default, selectedAssignee) {
                        if (selectedAssignee != null) {
                            showChallenges(wedding, selectedAssignee)
                        } else {
                            assigneeSelection(emptyList())
                        }
                    }
                )
        }

        GET("/search") { request ->
            val result = request.paramOrNull("q")?.ifBlank { null }
                ?.let { query -> wedding.assignees.filter { it.name.startsWith(query, ignoreCase = true) } }
                ?: emptyList()
            ServerResponse.status(HttpStatus.OK)
                .body(
                    partial { assignees(result) }
                )
        }

        POST("select-assignee/{id}") { request ->
            ServerResponse.status(HttpStatus.OK)
                .header("HX-Redirect", "/")
                .cookie(SelectedAssigneeCookie(request.pathVariable("id")))
                .build()
        }

        POST("complete/{id}") { request ->
            val id: ChallengeId = ChallengeId.fromString(request.pathVariable("id"))
            val completeChallenge = CompleteChallenge(id)
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

        POST("uncomplete/{id}") { request ->
            val id: ChallengeId = ChallengeId.fromString(request.pathVariable("id"))
            val undoCompleteChallenge = UndoCompletedChallenge(id)
            wedding.execute(undoCompleteChallenge)
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
            ServerResponse.status(HttpStatus.OK)
                .header("HX-Redirect", "/")
                .apply {
                    val selectedAssignee = request.getSelectedAssignee()
                    if (selectedAssignee != null) cookie(selectedAssignee.deleted())
                }
                .build()
        }

        POST("/toggle/{theme}") { request ->
            val theme = WeddingTheme.fromString(request.pathVariable("theme"))

            ServerResponse.status(HttpStatus.OK)
                .header("HX-Redirect", "/")
                .cookie(WeddingThemeCookie(theme))
                .build()
        }
    }

    private fun ServerRequest.getSelectedAssignee(): SelectedAssigneeCookie? =
        cookies().toSingleValueMap()[SelectedAssigneeCookie.key]?.let { SelectedAssigneeCookie(it.value) }

    private fun ServerRequest.getTheme(): WeddingTheme? =
        cookies().toSingleValueMap()[WeddingThemeCookie.key]?.let { WeddingThemeCookie(it.value) }?.theme
}


data class SelectedAssigneeCookie(val assigneeIdString: String) : Cookie(key, assigneeIdString) {
    val assigneeId: AssigneeId get() = AssigneeId.fromString(assigneeIdString)
    fun deleted(): SelectedAssigneeCookie = copy(assigneeIdString = assigneeIdString)
        .apply {
            setAttribute("expires", "Thu, 01 Jan 1970 00:00:00 GMT")
        }

    init {
        path = "/"
    }

    companion object {
        const val key = "selectedAssignee"
    }
}

data class WeddingThemeCookie(val themeAsString: String) : Cookie(key, themeAsString) {
    constructor(theme: WeddingTheme) : this(theme.toString())

    val theme: WeddingTheme get() = WeddingTheme.fromString(themeAsString)

    init {
        path = "/"
    }

    companion object {
        const val key = "weddingTheme"
    }
}