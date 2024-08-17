package be.kunlabora.crafters.wedding.web

import be.kunlabora.crafters.wedding.service.CompleteChallenge
import be.kunlabora.crafters.wedding.service.UndoCompletedChallenge
import be.kunlabora.crafters.wedding.service.WeddingBehavior
import be.kunlabora.crafters.wedding.service.domain.ChallengeId
import be.kunlabora.crafters.wedding.service.get
import be.kunlabora.crafters.wedding.web.ui.partial
import kotlinx.html.p
import org.springframework.http.HttpStatus
import org.springframework.web.servlet.function.RouterFunctionDsl
import org.springframework.web.servlet.function.ServerResponse

fun RouterFunctionDsl.challengeRoutes(wedding: WeddingBehavior) {
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
                    .body(partial { p { +"Oopsie! Something broke!"; +failure.message } })
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
                    .body(partial { p { +"Oopsie! Something broke!"; +failure.message } })
            }.get()
    }
}