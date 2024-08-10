package be.kunlabora.crafters.wedding.web.ui.test

import be.kunlabora.crafters.wedding.web.ui.partial
import kotlinx.html.FlowContent
import org.approvaltests.Approvals

fun verify(content: FlowContent.() -> Unit) {
    Approvals.verify(partial { content() })
}