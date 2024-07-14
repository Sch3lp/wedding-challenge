package be.kunlabora.crafters.kunlaquota.data

import be.kunlabora.crafters.kunlaquota.AddFailure
import be.kunlabora.crafters.kunlaquota.FetchQuotesFailed
import be.kunlabora.crafters.kunlaquota.QuoteAlreadyExists
import be.kunlabora.crafters.kunlaquota.service.Result
import be.kunlabora.crafters.kunlaquota.service.Result.Error
import be.kunlabora.crafters.kunlaquota.service.Result.Ok
import be.kunlabora.crafters.kunlaquota.service.domain.Quote
import be.kunlabora.crafters.kunlaquota.service.domain.QuoteId
import be.kunlabora.crafters.kunlaquota.service.domain.QuoteRepository
import org.springframework.data.annotation.Id
import org.springframework.data.jdbc.core.JdbcAggregateTemplate
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import java.time.LocalDateTime

class DataJDBCQuoteRepository(
    private val jdbcAggregateTemplate: JdbcAggregateTemplate,
    private val quoteDAO: QuoteDAO,
) : QuoteRepository {

    /*
     * JdbcAggregateTemplate required, because we decide in code when an entity gets a new EntityId
     * Using the DAO would delegate to an update method instead of an insert method ¯\_(ツ)_/¯
     */
    override fun store(quote: Quote): Result<AddFailure, Quote> =
        if (quoteDAO.existsById(quote.id.value)) Error(QuoteAlreadyExists("Quote already exists!"))
        else Ok(jdbcAggregateTemplate.insert(quote.toRecord()).toQuote())

    override fun findAll(): Result<FetchQuotesFailed, List<Quote>> =
        try {
            Ok(quoteDAO.findAllSortedDesc().map { quoteRecord -> quoteRecord.toQuote() })
        } catch (ex: Exception) {
            Error(FetchQuotesFailed)
        }

    private fun Quote.toRecord() =
        QuoteRecord(id = id.value, at = at, lines = lines.map { it.toRecord() }.toSet())

    private fun Quote.Line.toRecord() =
        QuoteLineRecord(order, name, text)

    private fun QuoteRecord.toQuote() =
        Quote(id = QuoteId.fromString(id), at = at, lines = lines.map { line -> line.toQuoteLine() })

    private fun QuoteLineRecord.toQuoteLine() =
        Quote.Line(order, name, text)

}

@Table("quote")
data class QuoteRecord(
    @Id
    val id: String,
    val at: LocalDateTime,
    val lines: Set<QuoteLineRecord>,
)

@Table("quote_lines")
data class QuoteLineRecord(
    val order: Int,
    val name: String,
    val text: String,
)

interface QuoteDAO : CrudRepository<QuoteRecord, String>, PagingAndSortingRepository<QuoteRecord, String> {

    @Query("select * from quote order by at desc")
    fun findAllSortedDesc(): List<QuoteRecord>
}