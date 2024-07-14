package be.kunlabora.crafters.kunlaquota.web

import be.kunlabora.crafters.kunlaquota.service.IQuotes
import be.kunlabora.crafters.kunlaquota.web.rest.apiRoutes
import be.kunlabora.crafters.kunlaquota.web.ui.baseUiUrl
import be.kunlabora.crafters.kunlaquota.web.ui.uiRoutes
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.function.router


@Configuration
class WebConfig : WebMvcConfigurer {

    @Bean
    fun apiRoutesBean(quotes: IQuotes) = router {
        "/api".nest(apiRoutes(quotes))
    }

    @Bean
    fun uiRoutesBean(quotes: IQuotes) = router {
        baseUiUrl.nest(uiRoutes(quotes))
    }
}