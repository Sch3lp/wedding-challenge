package be.kunlabora.crafters.wedding

sealed interface Failure {
    val message: String get() = javaClass.simpleName
}
sealed interface ChallengeFailure : Failure

data class FetchFailure(override val message: String) : ChallengeFailure
