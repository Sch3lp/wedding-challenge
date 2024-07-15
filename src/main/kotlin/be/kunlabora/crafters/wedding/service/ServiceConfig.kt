package be.kunlabora.crafters.wedding.service

import be.kunlabora.crafters.wedding.data.InMemChallenges
import be.kunlabora.crafters.wedding.service.domain.Assignee
import be.kunlabora.crafters.wedding.service.domain.AssigneeId
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
    Assignee(name = "Tim", id = AssigneeId.fromString("929c0b66-1bde-4227-b0c5-7334e6c3601b")),
    Assignee(name = "Megan", id = AssigneeId.fromString("279e4a6d-ff18-4cae-829c-e99709233878")),
    Assignee(name = "Martine", id = AssigneeId.fromString("c33a2587-dc51-45dc-8005-52434a161090")),
    Assignee(name = "Felicien", id = AssigneeId.fromString("564b2727-7bd7-4249-babf-781bdffa0567")),
    Assignee(name = "Ludo", id = AssigneeId.fromString("48efae5b-3cac-4f7f-938b-ddf3478ea674")),
    Assignee(name = "Lut", id = AssigneeId.fromString("16f44805-b207-467d-a317-e427c97202a4")),
    Assignee(name = "Maria", id = AssigneeId.fromString("9ac2ee4b-912d-4d5d-a13c-1659d42b9159")),
    Assignee(name = "Tom", id = AssigneeId.fromString("97221262-a649-4bca-9489-c6d3287b6c1e")),
    Assignee(name = "Lukas", id = AssigneeId.fromString("33f45cc5-86a4-4a49-9eea-eea96b4e8484")),
    Assignee(name = "Inés", id = AssigneeId.fromString("b374d45c-9dea-4eb8-9106-b91386e6f228")),
    Assignee(name = "Jessica", id = AssigneeId.fromString("cb691da6-2af0-47ad-b810-dfd71534247a")),
    Assignee(name = "Elke", id = AssigneeId.fromString("a73651ca-ed3f-4a20-b4bc-091e4cebd9ee")),
    Assignee(name = "Kristof", id = AssigneeId.fromString("acbebd8d-022f-4243-baa6-45e8e2e860e5")),
    Assignee(name = "Ronin", id = AssigneeId.fromString("6fec5228-0aae-48c9-8321-79fee78cbec8")),
    Assignee(name = "Catrice", id = AssigneeId.fromString("e72392da-e1f1-44fb-9d31-e187663dc4d6")),
    Assignee(name = "Isabelle", id = AssigneeId.fromString("bd2eed11-33eb-400a-b32b-7a3aaf260881")),
    Assignee(name = "Michael", id = AssigneeId.fromString("3543728d-4b3b-4208-b89c-685c980bc9b7")),
    Assignee(name = "Elise", id = AssigneeId.fromString("ddaf8156-2cb7-4371-b312-25739f8412e5")),
    Assignee(name = "Pepijn", id = AssigneeId.fromString("f2025182-b222-42e6-bf18-77ff5dcfec36")),
    Assignee(name = "Nancy", id = AssigneeId.fromString("16697265-cc76-4b05-bfc4-4022a92ab70c")),
    Assignee(name = "Jurgen", id = AssigneeId.fromString("33cb7707-c7bf-441f-88f2-44521cf737af")),
    Assignee(name = "Vicky", id = AssigneeId.fromString("70a46221-b2ee-4e8b-939b-9f85203b0995")),
    Assignee(name = "Guido", id = AssigneeId.fromString("28d00e33-5af4-4d3c-befd-a18c81941e4e")),
    Assignee(name = "Stijn", id = AssigneeId.fromString("304850a1-d83e-4ced-9ea8-8052c8470c4d")),
    Assignee(name = "Jessy", id = AssigneeId.fromString("1a2fca33-6f69-4ab6-ab0e-d6dd844d296d")),
    Assignee(name = "Seppe", id = AssigneeId.fromString("9245054f-81b1-47bd-98ea-3959ee2a5cbe")),
    Assignee(name = "Famke", id = AssigneeId.fromString("3afcb210-7598-483f-bc9a-75143aa024fb")),
    Assignee(name = "Sam", id = AssigneeId.fromString("69e72195-4bcd-4b17-8db5-56b857d7e097")),
    Assignee(name = "Kelly", id = AssigneeId.fromString("2fa62813-e72d-4883-80fe-eb10c36e19eb")),
    Assignee(name = "Cynthia", id = AssigneeId.fromString("b090c771-92b6-4639-8c14-0aeb46fe98b3")),
    Assignee(name = "Johan", id = AssigneeId.fromString("5a7dc094-2266-47b9-a77b-65670ca4fada")),
    Assignee(name = "Katrien", id = AssigneeId.fromString("6a08c5ac-862b-41cf-b982-9eadf7edf030")),
    Assignee(name = "Sam", id = AssigneeId.fromString("4ab21861-1e9c-4550-a282-c1ddaf603f27")),
    Assignee(name = "Lien", id = AssigneeId.fromString("0e3f339a-6880-4828-a9d3-398bc2033e2a")),
    Assignee(name = "Pieter", id = AssigneeId.fromString("808946f6-d648-4a70-83b6-037821024b0c")),
    Assignee(name = "Jan J.", id = AssigneeId.fromString("0d85444c-98df-44df-ba56-98a560489885")),
    Assignee(name = "Els", id = AssigneeId.fromString("acde0dcb-8f12-4686-bf76-1107757f09aa")),
    Assignee(name = "Bart", id = AssigneeId.fromString("c5c9749f-ea1c-4a55-92f2-72af31f76793")),
    Assignee(name = "Eline B.", id = AssigneeId.fromString("a24465f1-d1e8-4cb0-a312-62d0699f9455")),
    Assignee(name = "Sara", id = AssigneeId.fromString("d95760f4-abea-4cba-918b-05a50275799e")),
    Assignee(name = "Jonathan", id = AssigneeId.fromString("f17e3be6-09c6-452f-827e-df40bb3bd779")),
    Assignee(name = "Steffi", id = AssigneeId.fromString("17f08676-5fe5-4436-93c0-b48b5986bb79")),
    Assignee(name = "Sander", id = AssigneeId.fromString("de745497-c1a0-4c5f-8865-0179f0067d9b")),
    Assignee(name = "Sofie", id = AssigneeId.fromString("54a1bbd1-7150-42fc-bed2-4c5045bdd7f4")),
    Assignee(name = "Jan A.", id = AssigneeId.fromString("ad340764-102c-4cbf-bcba-4b08982eca81")),
    Assignee(name = "Peter", id = AssigneeId.fromString("68232892-e466-4afa-948c-58ce7850baa6")),
    Assignee(name = "Maren", id = AssigneeId.fromString("823a977e-9da5-4ae3-aa42-bfc29278625f")),
    Assignee(name = "Sigi", id = AssigneeId.fromString("8727a0f5-7b53-4fbc-b8a6-07d5e1033113")),
    Assignee(name = "Ruben", id = AssigneeId.fromString("a1ccd754-edf4-40e9-9f4d-7593ba885ffc")),
    Assignee(name = "Jonas V.", id = AssigneeId.fromString("a5cbb870-90ef-45d5-907e-861516d71d34")),
    Assignee(name = "Jana", id = AssigneeId.fromString("ddfb15fd-58b2-4fc5-bb29-75c8a66753b8")),
    Assignee(name = "Evelien", id = AssigneeId.fromString("8c074ea1-c94e-4720-8677-72fd85e748b1")),
    Assignee(name = "Steve", id = AssigneeId.fromString("2a835f83-44b7-4bc9-8f5b-6901a39bc5c2")),
    Assignee(name = "Steven", id = AssigneeId.fromString("5a48586e-a21e-48be-91a6-00b40f191f56")),
    Assignee(name = "Maksim", id = AssigneeId.fromString("6535a7c4-135f-4e06-a9c4-9ca58483266c")),
    Assignee(name = "Floor", id = AssigneeId.fromString("0f649a2c-0666-4bd1-9fe4-524f83dfb191")),
    Assignee(name = "Daan", id = AssigneeId.fromString("3a6708e0-1d64-4c76-adc6-e41960c54159")),
    Assignee(name = "Jan S.", id = AssigneeId.fromString("d6a47929-7e40-4d1b-9a0f-0115a6e9605b")),
    Assignee(name = "Vincent", id = AssigneeId.fromString("b3dbc86e-2ba2-4a14-914d-f7cee73ec091")),
    Assignee(name = "Eline", id = AssigneeId.fromString("cad91f96-1fb5-4318-b8a6-af5eb6c2f9c3")),
    Assignee(name = "Bruno", id = AssigneeId.fromString("e2002972-1965-4235-a982-8866e99d533a")),
    Assignee(name = "Tinati", id = AssigneeId.fromString("091de2fb-aea9-46c3-8882-76c4301aea24")),
    Assignee(name = "Catherine", id = AssigneeId.fromString("0426844e-9a70-442d-aaf9-f8f72e0adead")),
    Assignee(name = "Catherine's hubby", id = AssigneeId.fromString("6a3ccf81-bb2c-4a35-86f8-0a1937431341")),
    Assignee(name = "Alexandra", id = AssigneeId.fromString("6ecadb2c-bc53-4e5b-8b75-ff50581fe2f0")),
    Assignee(name = "Jonas", id = AssigneeId.fromString("209d4051-1ef6-4be9-be84-31c6f47a316b")),
    Assignee(name = "Rodrigo", id = AssigneeId.fromString("0f7e6243-2740-4d58-a0e2-e589e9c4183f")),
    Assignee(name = "Chana", id = AssigneeId.fromString("c679a0f1-658a-4295-b131-75dded0ad5f9")),
)

private val allChallenges = listOf(
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
        description = "Find the inventor of the PHUCIT game that caused Tim to crowdsurf for the first time in his life.",
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
)