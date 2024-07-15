package be.kunlabora.crafters.wedding.web.ui.components

import kotlinx.html.BODY
import kotlinx.html.a
import kotlinx.html.div
import kotlinx.html.nav

object NavBar {
    fun BODY.navbar() {
        nav("navbar is-primary") {
            div("navbar-brand") {
                a(classes = "navbar-item", href = "/") {
                    +"Wedding"
                }
            }
        }
    }
}