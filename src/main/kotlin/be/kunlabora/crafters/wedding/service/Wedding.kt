package be.kunlabora.crafters.wedding.service

import be.kunlabora.crafters.wedding.ChallengeFailure
import be.kunlabora.crafters.wedding.service.domain.Assignee
import be.kunlabora.crafters.wedding.service.domain.AssigneeId
import be.kunlabora.crafters.wedding.service.domain.Challenge
import be.kunlabora.crafters.wedding.service.domain.Challenges

typealias AssigneeName = String
typealias ChallengeDescription = String

interface WeddingBehavior {
    val assignees: List<Assignee>

    fun execute(completeChallenge: CompleteChallenge): Result<ChallengeFailure, Challenge>
    fun execute(undoCompletedChallenge: UndoCompletedChallenge): Result<ChallengeFailure, Challenge>

    fun findAllChallengesFor(assigneeId: AssigneeId): List<Challenge>
    fun allCompletedChallenges(except: AssigneeId): List<Pair<AssigneeName, ChallengeDescription>>
}

class Wedding(
    private val challenges: Challenges,
    override val assignees: List<Assignee>,
) : WeddingBehavior {

    override fun execute(
        completeChallenge: CompleteChallenge,
    ): Result<ChallengeFailure, Challenge> {
        return challenges.getById(completeChallenge.id)
            .map { challenge ->
                challenge.markAsCompleted().store()
            }
    }

    override fun execute(
        undoCompletedChallenge: UndoCompletedChallenge,
    ): Result<ChallengeFailure, Challenge> {
        return challenges.getById(undoCompletedChallenge.id)
            .map { challenge ->
                challenge.markAsUncompleted().store()
            }
    }

    private fun Challenge.store() = challenges.store(this)

    override fun findAllChallengesFor(assigneeId: AssigneeId): List<Challenge> =
        challenges.findAll().filter { it.assignee == assigneeId }

    override fun allCompletedChallenges(except: AssigneeId): List<Pair<AssigneeName, ChallengeDescription>> =
        challenges.findAll()
            .filter { it.completed }
            .mapNotNull { challenge ->
                assignees.filterNot { it.id == except }.firstOrNull { it.id == challenge.assignee }?.let { it.name to challenge.description }
            }

}
