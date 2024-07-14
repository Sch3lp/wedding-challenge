package be.kunlabora.crafters.kunlaquota.web.ui.components

import be.kunlabora.crafters.kunlaquota.web.ui.baseUiUrl
import be.kunlabora.crafters.kunlaquota.web.ui.path
import kotlinx.html.BODY
import kotlinx.html.a
import kotlinx.html.div
import kotlinx.html.nav

object NavBar {
    fun BODY.navbar() {
        nav("navbar is-primary") {
            div("navbar-brand") {
                a(classes = "navbar-item", href = baseUiUrl) {
                    +"KunlaQuota"
                }
            }
            div("navbar-end") {
                div("navbar-item") {
                    div("buttons") {
                        a(classes = "button is-light", href = baseUiUrl.path("new")) {
                            +"Add New Quote"
                        }
                    }
                }
            }
        }
    }
}