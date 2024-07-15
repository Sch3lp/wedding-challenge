package be.kunlabora.crafters.wedding.service

import be.kunlabora.crafters.wedding.ChallengeFailure
import be.kunlabora.crafters.wedding.service.domain.Assignee
import be.kunlabora.crafters.wedding.service.domain.AssigneeId
import be.kunlabora.crafters.wedding.service.domain.Challenge
import be.kunlabora.crafters.wedding.service.domain.Challenges

interface WeddingBehavior {
    val assignees: List<Assignee>

    fun execute(
        completedChallenge: CompletedChallenge,
    ): Result<ChallengeFailure, Challenge>

    fun findAllChallengesFor(assigneeId: AssigneeId): List<Challenge>
}

class Wedding(
    private val challenges: Challenges,
    override val assignees: List<Assignee>,
) : WeddingBehavior {

    override fun execute(
        completedChallenge: CompletedChallenge,
    ): Result<ChallengeFailure, Challenge> {
        return challenges.getById(completedChallenge.id)
            .map { challenge ->
                challenge.markAsCompleted().store()
            }
    }

    private fun Challenge.store() = challenges.store(this)

    override fun findAllChallengesFor(assigneeId: AssigneeId): List<Challenge> = challenges.findAll().filter { it.assignee == assigneeId }

}
