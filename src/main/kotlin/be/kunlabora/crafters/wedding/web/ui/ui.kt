package be.kunlabora.crafters.wedding.web.ui

import be.kunlabora.crafters.wedding.service.domain.Assignee
import be.kunlabora.crafters.wedding.web.ui.components.Hero.hero
import kotlinx.html.*
import kotlinx.html.stream.appendHTML
import java.io.StringWriter

sealed interface WeddingTheme {
    val value: String
    data object Dark: WeddingTheme {
        override val value = "dark"
    }
    data object Light: WeddingTheme {
        override val value = "light"
    }

    companion object {
        val default = Dark

        fun fromString(string: String): WeddingTheme = when (string) {
            Dark.toString() -> Dark
            Light.toString() -> Light
            else -> default
        }
    }
}


fun wrapper(title: String, currentTheme: WeddingTheme, selectedAssignee: Assignee?, block: BODY.() -> Unit) =
    StringWriter().appendHTML().html {
        attributes["data-theme"] = currentTheme.value
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
                href = "https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css"
            )
            script(src = "https://unpkg.com/htmx.org@2.0.0") {}
            script(src = "https://unpkg.com/hyperscript.org@0.9.12") {}
        }
        body {
            hero(currentTheme, selectedAssignee)
            div(classes = "block") { id = "errorMessages" }
            block()
            div { id = "modals-here" }
        }
    }.toString()

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

fun String.path(vararg paths: String): String =
    buildList {
        add(this@path)
        addAll(paths)
    }.joinToString("/")