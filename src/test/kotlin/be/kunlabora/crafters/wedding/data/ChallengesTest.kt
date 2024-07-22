package be.kunlabora.crafters.wedding.data

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ChallengesTest {

    @Test
    fun `Verify all challenges have an assignee`() {
        val challengesPerName =
            allChallenges.map { challenge -> assignees.first { assignee -> challenge.assignee == assignee.id }.name to challenge.description }

        assertThat(challengesPerName.also { display(it) }).hasSize(allChallenges.size)
    }

    @Test
    fun `All unique challenges are, well, unique`() {
        assertThat(uniqueChallenges.map { it.description }.toSet()).hasSize(uniqueChallenges.size)
    }

    @Test
    fun `Everybody else gets a default challenge`() {
        assertThat(allChallenges).hasSize(assignees.size)
    }
}

private fun display(pairs: List<Pair<String, String>>) {
    print(pairs.joinToString("\n") { (name, description) -> "$name:  \t$description" })
}