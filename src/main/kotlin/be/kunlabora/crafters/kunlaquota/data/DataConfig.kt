package be.kunlabora.crafters.kunlaquota.data

import be.kunlabora.crafters.kunlaquota.service.domain.QuoteRepository
import be.kunlabora.crafters.kunlaquota.service.domain.QuoteShareRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jdbc.core.JdbcAggregateTemplate
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories

@Configuration
@EnableJdbcRepositories(basePackages = ["be.kunlabora.crafters.kunlaquota.data"])
class DataConfig {

    @Bean
    fun quoteRepository(jdbcAggregateTemplate: JdbcAggregateTemplate, quoteDAO: QuoteDAO) : QuoteRepository =
        DataJDBCQuoteRepository(jdbcAggregateTemplate, quoteDAO)

    @Bean
    fun quoteShareRepository(jdbcAggregateTemplate: JdbcAggregateTemplate, quoteShareDAO: QuoteShareDAO) : QuoteShareRepository =
        DBQuoteShareRepository(jdbcAggregateTemplate, quoteShareDAO)

}