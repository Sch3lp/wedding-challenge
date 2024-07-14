package be.kunlabora.crafters.kunlaquota.service

import be.kunlabora.crafters.kunlaquota.AddFailure
import be.kunlabora.crafters.kunlaquota.QuoteIsInvalid
import be.kunlabora.crafters.kunlaquota.service.Result.Error
import be.kunlabora.crafters.kunlaquota.service.Result.Ok
import be.kunlabora.crafters.kunlaquota.service.domain.Quote
import be.kunlabora.crafters.kunlaquota.service.domain.QuoteId
import com.fasterxml.jackson.annotation.JsonTypeInfo
import java.util.*

typealias IdProvider = () -> String

val uuidStringProvider: IdProvider = { UUID.randomUUID().toString() }

@Suppress("unused")
class EntityId<E> private constructor(val value: String) {
    companion object {
        fun <E> new(idProvider: IdProvider = uuidStringProvider): EntityId<E> =
            EntityId(idProvider())

        fun <E> fromString(value: String): EntityId<E> = EntityId(UUID.fromString(value).toString())
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EntityId<*>

        return value == other.value
    }

    override fun hashCode(): Int = value.hashCode()

    override fun toString() = "EntityId($value)"
}

//marker interface
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
sealed interface Command
data class AddQuote(val lines: List<Quote.Line>) : Command {

    fun validate(): Result<AddFailure, AddQuote> = validateLines().map { clean() }

    private fun validateLines() = when {
            onlyHasEmptyLines() -> Error(QuoteIsInvalid("A Quote needs at least one Line."))
            someLinesHaveSameOrder() -> Error(QuoteIsInvalid("Can't have multiple lines with the same order."))
            someLinesHaveNoName() -> Error(QuoteIsInvalid("A Quote Line needs a name."))
            someLinesHaveNoText() -> Error(QuoteIsInvalid("A Quote Line needs text."))
            else -> Ok(this)
        }

    private fun AddQuote.onlyHasEmptyLines() =
        lines.filterNot { it.text.isBlank() && it.name.isBlank() }.isEmpty()

    private fun AddQuote.someLinesHaveSameOrder() =
        lines.map { it.order }.toSet().size < lines.size

    private fun AddQuote.someLinesHaveNoName() =
        lines.any { line -> line.name.isBlank() && line.text.isNotBlank() }

    private fun AddQuote.someLinesHaveNoText() =
        lines.any { line -> line.text.isBlank() && line.name.isNotBlank() }

    private fun clean(): AddQuote = copy(
        lines = lines.dropBlankLines().reindex()
    )

    private fun List<Quote.Line>.dropBlankLines() =
        filterNot { it.name.isBlank() && it.text.isBlank() }

    private fun List<Quote.Line>.reindex() =
        sortedBy { it.order }
            .mapIndexed { index, line -> line.copy(order = index + 1) }
}

data class ShareQuote(val id: QuoteId) : Command

sealed class Result<out F, out T> {
    data class Error<F>(val value: F) : Result<F, Nothing>()
    data class Ok<T>(val value: T) : Result<Nothing, T>()

    fun valueOrThrow() = when (this) {
        is Error -> error("You expected an Ok, but got an Error of ${this.value}")
        is Ok -> this.value
    }

    fun <R> map(block: (T) -> R): Result<F, R> =
        when (this) {
            is Error -> this
            is Ok -> Ok(block(this.value))
        }

    fun <R> flatMap(block: (T) -> Result<@UnsafeVariance F, R>): Result<F, R> =
        when (this) {
            is Error -> this
            is Ok -> block(this.value)
        }

    fun recover(recovery: (F) -> @UnsafeVariance T): Result<T, T> =
        when (this) {
            is Error -> Ok(recovery(this.value))
            is Ok -> this
        }
}


fun <T> Result<T, T>.get(): T =
    when (this) {
        is Error -> this.value
        is Ok -> this.value
    }
