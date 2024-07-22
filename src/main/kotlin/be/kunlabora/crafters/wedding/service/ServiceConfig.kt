package be.kunlabora.crafters.wedding.service

import be.kunlabora.crafters.wedding.data.InMemChallenges
import be.kunlabora.crafters.wedding.data.allChallenges
import be.kunlabora.crafters.wedding.data.assignees
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ServiceConfig {

    @Bean
    fun wedding(): Wedding {
        val challenges = InMemChallenges(allChallenges.associateBy { it.id }.toMutableMap())

        return Wedding(
            challenges = challenges,
            assignees = assignees
        )
    }
}