package be.kunlabora.crafters.wedding.web.ui

import kotlinx.html.*
import kotlinx.html.stream.appendHTML
import java.io.StringWriter

fun partial(block: FlowContent.() -> Unit): String {
    val writer = StringWriter()
    val consumer = writer.appendHTML()
    // hacky stuff so we don't have to return a wrapper div
    object : FlowContent {
        override val consumer = consumer
        override val attributes: MutableMap<String, String>
            get() = mutableMapOf()
        override val attributesEntries: Collection<Map.Entry<String, String>>
            get() = emptyList()
        override val emptyTag: Boolean
            get() = true
        override val inlineTag: Boolean
            get() = true
        override val namespace: String?
            get() = null
        override val tagName: String
            get() = ""
    }.block()
    return writer.toString()
}

fun wrapper(
    title: String,
    content: FlowContent.() -> Unit
) = StringWriter().appendHTML().html {
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
            href = "https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css"
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

        content()
    }
}.toString()