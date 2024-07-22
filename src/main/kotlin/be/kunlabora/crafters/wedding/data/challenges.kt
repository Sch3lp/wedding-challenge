package be.kunlabora.crafters.wedding.data

import be.kunlabora.crafters.wedding.service.domain.Challenge

val allChallenges = listOf(
    Challenge(
        description = "Find the person who made Megan’s figure skating dress.",
        assignee = assignees.first { it.name == "Lut" }.id,
    ),
    Challenge(
        description = "Find the people whose stag-do involved dressing up like grease.",
        assignee = assignees.first { it.name == "Kristof" }.id,
    ),
    Challenge(
        description = "Find the person that stole a StuBru flag.",
        assignee = assignees.first { it.name == "Michael" }.id,
    ),
    Challenge(
        description = "Find the person who lived in Poland.",
        assignee = assignees.first { it.name == "Nancy" }.id,
    ),
    Challenge(
        description = "Find the person that maxxed 210kg deadlifts (4 sets).",
        assignee = assignees.first { it.name == "Jurgen" }.id,
    ),
    Challenge(
        description = "Find someone who also knows how to clear the local dns cache.",
        assignee = assignees.first { it.name == "Stijn" }.id,
    ),
    Challenge(
        description = "Find the first people that Megan ever met in Belgium.",
        assignee = assignees.first { it.name == "Kelly" }.id,
    ),
    Challenge(
        description = "Find someone that snowboards the opposite of what Tim snowboards (goofy or regular, which one is it?).",
        assignee = assignees.first { it.name == "Pieter" }.id,
    ),
    Challenge(
        description = "Find the person who’s moving to Tenerife.",
        assignee = assignees.first { it.name == "Els" }.id,
    ),
    Challenge(
        description = "Find the person that maxxed 150kg deadlifts (4 sets).",
        assignee = assignees.first { it.name == "Sander" }.id,
    ),
    Challenge(
        description = "Find the person that toured the USA in a shoe-gaze band.",
        assignee = assignees.first { it.name == "Sofie" }.id,
    ),
    Challenge(
        description = "Find at least 4 people with Jan in their name.",
        assignee = assignees.first { it.name == "Maren" }.id,
    ),
    Challenge(
        description = "Find three people that speak Russian and have a degree in philosophy.",
        assignee = assignees.first { it.name == "Ruben" }.id,
    ),
    Challenge(
        description = "Find all the people that did ultimate frisbee.",
        assignee = assignees.first { it.name == "Jonas V." }.id,
    ),
    Challenge(
        description = "Find all the people that did ultimate frisbee.",
        assignee = assignees.first { it.name == "Steven" }.id,
    ),
    Challenge(
        description = "Find the inventor of the PHUCIT game that had Tim crowdsurfing for the first time in his life.",
        assignee = assignees.first { it.name == "Daan" }.id,
    ),
    Challenge(
        description = "Find the BB grandma assassin.",
        assignee = assignees.first { it.name == "Jan S." }.id,
    ),
    Challenge(
        description = "Find Tim's fragbuddies.",
        assignee = assignees.first { it.name == "Vincent" }.id,
    ),
    Challenge(
        description = "Find Tim’s boardgame/snowcase friends.",
        assignee = assignees.first { it.name == "Tinati" }.id,
    ),
    Challenge(
        description = "Find Megan’s figure skating coach.",
        assignee = assignees.first { it.name == "Chana" }.id,
    ),
    Challenge(
        description = "Give a high-five to Ronin.",
        assignee = assignees.first { it.name == "Sam Vdw." }.id,
    ),
    Challenge(
        description = "Give a low-five to Sam.",
        assignee = assignees.first { it.name == "Ronin" }.id,
    ),
    Challenge(
        description = "Give a high-five to Catrice.",
        assignee = assignees.first { it.name == "Lien" }.id,
    ),
    Challenge(
        description = "Give a low-five to Lien.",
        assignee = assignees.first { it.name == "Catrice" }.id,
    ),
    Challenge(
        description = "Give a speech.",
        assignee = assignees.first { it.name == "Johan" }.id,
    ),
)