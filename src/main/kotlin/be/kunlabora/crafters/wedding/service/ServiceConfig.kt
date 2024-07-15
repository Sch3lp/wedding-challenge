package be.kunlabora.crafters.wedding.service

import be.kunlabora.crafters.wedding.data.InMemChallenges
import be.kunlabora.crafters.wedding.service.domain.Assignee
import be.kunlabora.crafters.wedding.service.domain.Challenge
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

private val assignees = listOf(
    Assignee("Tim"),
    Assignee("Megan"),
    Assignee("Martine"),
    Assignee("Felicien"),
    Assignee("Ludo"),
    Assignee("Lut"),
    Assignee("Maria"),
    Assignee("Tom"),
    Assignee("Lukas"),
    Assignee("Inés"),
    Assignee("Jessica"),
    Assignee("Elke"),
    Assignee("Kristof"),
    Assignee("Ronin"),
    Assignee("Catrice"),
    Assignee("Isabelle"),
    Assignee("Michael"),
    Assignee("Elise"),
    Assignee("Pepijn"),
    Assignee("Nancy"),
    Assignee("Jurgen"),
    Assignee("Vicky"),
    Assignee("Guido"),
    Assignee("Stijn"),
    Assignee("Jessy"),
    Assignee("Seppe"),
    Assignee("Famke"),
    Assignee("Sam"),
    Assignee("Kelly"),
    Assignee("Cynthia"),
    Assignee("Johan"),
    Assignee("Katrien"),
    Assignee("Sam"),
    Assignee("Lien"),
    Assignee("Pieter"),
    Assignee("Jan J."),
    Assignee("Els"),
    Assignee("Bart"),
    Assignee("Eline B."),
    Assignee("Sara"),
    Assignee("Jonathan"),
    Assignee("Steffi"),
    Assignee("Sander"),
    Assignee("Sofie"),
    Assignee("Jan A."),
    Assignee("Peter"),
    Assignee("Maren"),
    Assignee("Sigi"),
    Assignee("Ruben"),
    Assignee("Jonas V."),
    Assignee("Jana"),
    Assignee("Evelien"),
    Assignee("Steve"),
    Assignee("Steven"),
    Assignee("Maksim"),
    Assignee("Floor"),
    Assignee("Daan"),
    Assignee("Jan S."),
    Assignee("Vincent"),
    Assignee("Eline"),
    Assignee("Bruno"),
    Assignee("Tinati"),
    Assignee("Catherine"),
    Assignee("Catherine's hubby"),
    Assignee("Alexandra"),
    Assignee("Jonas"),
    Assignee("Rodrigo"),
    Assignee("Chana"),
)

private val allChallenges = listOf(
    Challenge(
        description = "Find the person who made Megan’s figure skating dress.",
        assignee = assignees.first { it.name == "Lut" }.id,
    ),
)