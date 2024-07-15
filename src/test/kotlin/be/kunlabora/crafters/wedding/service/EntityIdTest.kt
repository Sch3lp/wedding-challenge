package be.kunlabora.crafters.wedding.service

import be.kunlabora.crafters.wedding.service.domain.Challenge
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class EntityIdTest {
    @Test
    fun `when not passing in a IdFactory, a random UUID is used`() {
        val actual = EntityId.new<Challenge>()
        assertThat(actual.value).matches("""^[0-9a-fA-F]{8}\b-[0-9a-fA-F]{4}\b-[0-9a-fA-F]{4}\b-[0-9a-fA-F]{4}\b-[0-9a-fA-F]{12}${'$'}""")
    }

    @Test
    fun `when passing in an IdFactory, the IdFactory is used to generate the EntityId`() {
        val actual = EntityId.new<Challenge>(idProvider = {"FIXED"})
        assertThat(actual.value).isEqualTo("FIXED")
    }
}