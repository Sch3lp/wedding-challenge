package be.kunlabora.crafters.kunlaquota

sealed interface Failure {
    val message: String get() = javaClass.simpleName
}
sealed interface AddFailure : Failure
sealed interface ReadFailure : Failure
sealed interface ShareFailure : Failure

data class QuoteAlreadyExists(override val message: String) : AddFailure
data class QuoteIsInvalid(override val message: String) : AddFailure

data object FetchQuotesFailed : ReadFailure

data object ShareQuoteFailed : ShareFailure
