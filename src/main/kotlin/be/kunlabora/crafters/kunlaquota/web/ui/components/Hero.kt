package be.kunlabora.crafters.kunlaquota.web.ui.components

import kotlinx.html.*


object Hero {
    fun FlowContent.hero() {
        section(classes = "hero is-primary") {
            div(classes = "hero-body") {
                div(classes = "container") {
                    h1(classes = "title") {
                        +"KunlaQuota"
                    }
                    h2(classes = "subtitle") {
                        +"Share and enjoy funny quotes"
                    }
                }
            }
        }
    }
}