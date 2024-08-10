package be.kunlabora.crafters.wedding.web.ui

import be.kunlabora.crafters.wedding.service.domain.Assignee
import be.kunlabora.crafters.wedding.web.ui.test.verify
import be.kunlabora.crafters.wedding.web.welcome
import org.junit.jupiter.api.Test

class WelcomeTest {

    @Test
    fun `Welcome displays the assignee's name and a welcome message`() {
        verify { welcome(Assignee("snarf")) }
    }
}