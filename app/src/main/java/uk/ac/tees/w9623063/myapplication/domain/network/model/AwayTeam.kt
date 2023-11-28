package uk.ac.tees.w9623063.myapplication.domain.network.model

data class AwayTeam(
    val coaches: List<Coache>,
    val missing_players: List<Any>,
    val starting_lineups: List<StartingLineup>,
    val substitutes: List<Substitute>
)