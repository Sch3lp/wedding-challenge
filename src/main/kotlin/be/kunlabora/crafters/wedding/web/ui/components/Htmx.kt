package be.kunlabora.crafters.wedding.web.ui.components

import kotlinx.html.Tag

object Htmx {
    var Tag.hxGet: String
        get() = error("not implemented")
        set(value) {
            attributes["hx-get"] = value
        }
    var Tag.hxPost: String
        get() = error("not implemented")
        set(value) {
            attributes["hx-post"] = value
        }
    var Tag.hxTarget: String
        get() = error("not implemented")
        set(value) {
            attributes["hx-target"] = value
        }
    var Tag.hxTrigger: String
        get() = error("not implemented")
        set(value) {
            attributes["hx-trigger"] = value
        }
    var Tag.hxSwap: String
        get() = error("not implemented")
        set(value) {
            attributes["hx-swap"] = value
        }
    var Tag.hyper: String
        get() = error("not implemented")
        set(value) {
            attributes["_"] = value
        }
}