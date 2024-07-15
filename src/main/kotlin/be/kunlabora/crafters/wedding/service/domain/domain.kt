package be.kunlabora.crafters.wedding.service.domain

import be.kunlabora.crafters.wedding.service.EntityId
import java.time.LocalDateTime

typealias ChallengeId = EntityId<Challenge>
typealias AssigneeId = EntityId<Assignee>

data class Challenge(
    val id: ChallengeId = ChallengeId.new(),
    val description: String,
    val assignee: AssigneeId,
    val completed: Boolean = false,
    val completedOn: LocalDateTime? = null,
) {
    fun markAsCompleted(): Challenge =
        copy(completed = true, completedOn = LocalDateTime.now())
}

data class Assignee(
    val name: String,
    val id: AssigneeId = AssigneeId.new(),
)