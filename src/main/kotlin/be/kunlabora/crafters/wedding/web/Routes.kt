package be.kunlabora.crafters.wedding.web

import be.kunlabora.crafters.wedding.service.WeddingBehavior
import kotlinx.html.*
import kotlinx.html.stream.appendHTML
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.router
import java.io.StringWriter


@Configuration
class Routes {

    @Bean
    fun router(wedding: WeddingBehavior) = router {
        GET { request ->
            val title = "WeddingChallenge"
            val assignees = wedding.getAssignees()

            ServerResponse.status(HttpStatus.OK)
                .contentType(MediaType.TEXT_HTML)
                .body(
                    StringWriter().appendHTML().html {
                        attributes["data-theme"] = "light"

                        head {
                            title { +title }
                            meta(charset = "utf-8")
                            meta(name = "viewport", content = "width=device-width, initial-scale=1")
                            link(
                                rel = "stylesheet",
                                href = "https://cdn.jsdelivr.net/npm/bulma@1.0.1/css/bulma.min.css"
                            )
                            link(
                                rel = "stylesheet",
                                href = "https://cdn.jsdelivr.net/npm/@fortawesome/fontawesome-free@6.4.2/css/fontawesome.min.css"
                            )
                            script(src = "https://unpkg.com/htmx.org@2.0.0") {}
                            script(src = "https://unpkg.com/hyperscript.org@0.9.12") {}
                        }

                        body {
                            section(classes = "hero is-primary") {
                                div(classes = "hero-body") {
                                    div(classes = "container") {
                                        h1(classes = "title") {
                                            +"Tim & Megan's Wedding"
                                        }
                                        h2(classes = "subtitle") {
                                            +"Complete challenges as you party!"
                                        }
                                    }
                                }
                            }

                            div {
                                id = "selection"

                                p { +"Who are you?" }
                                div(classes = "select") {
                                    select {
                                        name = "assignee"
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
                    }.toString()
                )
        }
    }
}