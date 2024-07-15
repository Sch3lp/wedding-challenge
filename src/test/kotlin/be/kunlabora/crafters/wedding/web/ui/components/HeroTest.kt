package be.kunlabora.crafters.wedding.web.ui.components

import be.kunlabora.crafters.wedding.web.ui.components.Hero.hero
import be.kunlabora.crafters.wedding.web.ui.partial
import org.junit.jupiter.api.Test
import verify

class HeroTest {
    @Test
    fun `verify hero partial`() {
        partial { hero(theme, selectedAssigneeId) }.verify()
    }
}