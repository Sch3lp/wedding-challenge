package be.kunlabora.crafters.wedding.data

import be.kunlabora.crafters.wedding.service.domain.Challenge

val uniqueChallenges = listOf(
    "Lut" has "Find the person who made Megan’s figure skating dress.",
    "Kristof" has "Find the people whose stag-do involved dressing up like grease.",
    "Michael" has "Find the person that stole a StuBru flag.",
    "Nancy" has "Find the person who lived in Poland.",
    "Jurgen" has "Find the person that maxxed 210kg deadlifts (4 sets).",
    "Stijn" has "Find someone who also knows how to clear the local dns cache.",
    "Kelly" has "Find the first people that Megan ever met in Belgium.",
    "Pieter" has "Find someone that snowboards the opposite of what Tim snowboards (goofy or regular, which one is it?).",
    "Els" has "Find the person who’s moving to Tenerife.",
    "Sander" has "Find the person that maxxed 150kg deadlifts (4 sets).",
    "Sofie" has "Find the person that toured the USA in a shoe-gaze band.",
    "Maren" has "Find at least 4 people with Jan in their name.",
    "Ruben" has "Find three people that speak Russian and have a degree in philosophy.",
    "Jonas V." has "Find all the people that did ultimate frisbee.",
    "Steven" has "Find the person that used to live in China.",
    "Daan" has "Find the inventor of the PHUCIT game that had Tim crowdsurfing for the first time in his life.",
    "Jan S." has "Find the BB grandma assassin.",
    "Vincent" has "Find Tim's fragbuddies.",
    "Tinati" has "Find Tim’s boardgame/snowcase friends.",
    "Chana" has "Find Megan’s figure skating coach.",
    "Sam Vdw." has "Give a high-five to Ronin.",
    "Ronin" has "Give a low-five to Sam.",
    "Lien" has "Give a high-five to Catrice.",
    "Catrice" has "Give a low-five to Lien.",
    "Johan" has "Give a speech.",
)

val rest = (assignees.map { it.id } - uniqueChallenges.map { it.assignee }.toSet()).map { assigneeId ->
    Challenge(
        assignee = assigneeId,
        description = "Take a selfie with someone you don't know and send it to Megan or Tim."
    )
}

val allChallenges = uniqueChallenges + rest


private infix fun String.has(description: String) = Challenge(
    description = description,
    assignee = assignees.first { it.name == this }.id,
)

