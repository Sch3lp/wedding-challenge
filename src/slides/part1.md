# Part 1

## Wedding Challenge
QR Code at dinner table brings party guests to a web app.

They tell the web app who they are by selecting themselves out of a list.

They then get presented some kind of "challenge", such as "Make a selfie with someone you don't know and send it to Tim or Megan".

## Stack
"100% Kotlin": 
* SpringBoot to serve everything with the Kotlin router DSL.
* Backed by in memory lists that get instantiated at SpringBoot start-up time.
* Frontend consists of simply responding to HTTP requests with html that gets built using kotlinx.html, a DSL that allows us to build typesafe html documents.
* Dynamic behavior is provided by htmx, a simple javascript library that leverages HATEOAS and makes all html elements to trigger ajax requests. The html response is then simply used to replace the inner html of the element that triggered the request.
* We're making it pretty with Bulma, because stays mostly out of the way.

## LiveCoding
### Main screen
1. Routes.kt + start app in debug (for hot-reloading) and show http://localhost:8080
2. Extract method on selecting assignee (showSelection)
3. Extract method on "wrapper", manually add content: FlowContent.() -> Unit parameter
4. Move "wrapper" to ui root package
5. Move showSelection to ui.screens package

### Selecting assignee
1. Add hxPost to select and show network tab (assignee -> name attribute; uuid -> assignee.id.value)
2. Register new route : POST("/select"). Add hxTarget = "body" to completely overwrite everything. Hardcode content to be +"snarf"
3. Add "partial" function
```kotlin
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
```
4. Add container div to the p and select. Give it an id of "selection. Then switch hxTarget to "#selection". Show network tab with response.
5. Extract selected assignee id from request. Transform to AssigneeId. Find matching Assignee. Return Assignee.name + welcome text.

