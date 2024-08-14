package be.kunlabora.crafters.wedding.web.ui.screens

import be.kunlabora.crafters.wedding.service.domain.Assignee
import be.kunlabora.crafters.wedding.service.domain.AssigneeId
import be.kunlabora.crafters.wedding.web.ui.test.verify
import org.junit.jupiter.api.Test

class AssigneeSelectionTest {
    @Test
    fun `renders assignees in a dropdown`() {
        val assignees = listOf(
            Assignee(name = "Joris", AssigneeId.new { "1234" }),
            Assignee(name = "Jonathan", AssigneeId.new { "6789" }),
        )
        verify {
            showAssigneeSelection(assignees)
        }
    }
}