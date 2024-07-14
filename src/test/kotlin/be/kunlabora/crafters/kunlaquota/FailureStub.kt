package be.kunlabora.crafters.kunlaquota

import be.kunlabora.crafters.kunlaquota.service.Result
import be.kunlabora.crafters.kunlaquota.service.Result.Error

interface CanReturnFailure<in F: Failure> {
    fun failOnNext(fn: String, failure: F)
}

class FailureStub<F: Failure> : CanReturnFailure<F> {
    private val registeredFailures: MutableMap<String, F> = mutableMapOf()

    override fun failOnNext(fn: String, failure: F) {
        registeredFailures[fn] = failure
    }

    operator fun <T> invoke(fn: String, block: () -> Result<F, T>): Result<F, T> {
        return registeredFailures[fn]
            ?.let { Error(it) }
            ?: block()
    }
}