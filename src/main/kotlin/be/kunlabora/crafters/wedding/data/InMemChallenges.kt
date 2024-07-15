package be.kunlabora.crafters.wedding.data

import be.kunlabora.crafters.wedding.ChallengeFailure
import be.kunlabora.crafters.wedding.FetchFailure
import be.kunlabora.crafters.wedding.service.Result
import be.kunlabora.crafters.wedding.service.domain.Challenge
import be.kunlabora.crafters.wedding.service.domain.ChallengeId
import be.kunlabora.crafters.wedding.service.domain.Challenges

class InMemChallenges(
    private val challenges: MutableMap<ChallengeId, Challenge> = mutableMapOf()
) : Challenges {

    override fun store(challenge: Challenge): Challenge {
        challenges[challenge.id] = challenge
        return challenge
    }

    override fun findAll(): List<Challenge> =
        challenges.values.toList()

    override fun getById(challengeId: ChallengeId): Result<ChallengeFailure, Challenge> {
        return challenges[challengeId]?.let { Result.Ok(it) } ?: Result.Error(FetchFailure("challenge not found"))
    }
}