package be.kunlabora.crafters.wedding.service.domain

import be.kunlabora.crafters.wedding.ChallengeFailure
import be.kunlabora.crafters.wedding.service.Result

interface Challenges {
    fun store(challenge: Challenge): Challenge
    fun findAll(): List<Challenge>
    fun getById(challengeId: ChallengeId): Result<ChallengeFailure, Challenge>
}