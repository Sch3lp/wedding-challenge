package be.kunlabora.crafters.kunlaquota.service

import be.kunlabora.crafters.kunlaquota.service.domain.HashedQuoteShareProvider
import be.kunlabora.crafters.kunlaquota.service.domain.QuoteRepository
import be.kunlabora.crafters.kunlaquota.service.domain.QuoteShareProvider
import be.kunlabora.crafters.kunlaquota.service.domain.QuoteShareRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ServiceConfig {

    @Bean
    fun quotes(
        quoteRepository: QuoteRepository,
        quoteShareProvider: QuoteShareProvider,
        quoteShareRepository: QuoteShareRepository,
    ) = Quotes(quoteRepository, quoteShareProvider, quoteShareRepository)

    @Bean
    fun quoteShareProvider(): QuoteShareProvider = HashedQuoteShareProvider()
}