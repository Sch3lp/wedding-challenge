package be.kunlabora.crafters.wedding.service

import be.kunlabora.crafters.wedding.service.Result.Error
import be.kunlabora.crafters.wedding.service.Result.Ok
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
