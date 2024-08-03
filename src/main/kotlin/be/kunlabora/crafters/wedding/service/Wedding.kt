package be.kunlabora.crafters.wedding.service

import be.kunlabora.crafters.wedding.ChallengeFailure
import be.kunlabora.crafters.wedding.service.domain.*
import com.fasterxml.jackson.annotation.JsonTypeInfo

typealias AssigneeName = String
typealias ChallengeDescription = String

//marker interface
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
sealed interface Command
data class CompleteChallenge(val id: ChallengeId) : Command
data class UndoCompletedChallenge(val id: ChallengeId) : Command

interface WeddingBehavior {
    fun getAssignees(): List<Assignee>

    fun execute(completeChallenge: CompleteChallenge): Result<ChallengeFailure, Challenge>
    fun execute(undoCompletedChallenge: UndoCompletedChallenge): Result<ChallengeFailure, Challenge>

    fun findAllChallengesFor(assigneeId: AssigneeId): List<Challenge>
    fun allCompletedChallenges(except: AssigneeId): List<Pair<AssigneeName, ChallengeDescription>>
}

class Wedding(
    private val challenges: Challenges,
    private val assignees: List<Assignee>,
) : WeddingBehavior {

    override fun getAssignees(): List<Assignee> = assignees

    override fun execute(
        completeChallenge: CompleteChallenge,
    ): Result<ChallengeFailure, Challenge> =
        challenges.getById(completeChallenge.id)
            .map { challenge -> challenge.markAsCompleted().store() }

    override fun execute(
        undoCompletedChallenge: UndoCompletedChallenge,
    ): Result<ChallengeFailure, Challenge> =
        challenges.getById(undoCompletedChallenge.id)
            .map { challenge -> challenge.markAsUncompleted().store() }

    override fun findAllChallengesFor(assigneeId: AssigneeId): List<Challenge> =
        challenges.findAll().filter { it.assignee == assigneeId }

    override fun allCompletedChallenges(except: AssigneeId): List<Pair<AssigneeName, ChallengeDescription>> =
        challenges.findAll()
            .filter { it.completed }
            .mapNotNull { challenge ->
                getAssignees().filterNot { it.id == except }
                    .firstOrNull { it.id == challenge.assignee }
                    ?.let { it.name to challenge.description }
            }

    private fun Challenge.store() = challenges.store(this)
}
