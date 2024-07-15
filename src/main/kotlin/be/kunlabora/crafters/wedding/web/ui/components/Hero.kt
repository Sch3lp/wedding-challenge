package be.kunlabora.crafters.wedding.web.ui.components

import kotlinx.html.*


object Hero {
    fun FlowContent.hero() {
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
    }
}