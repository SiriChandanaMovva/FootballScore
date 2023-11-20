package uk.ac.tees.w9623063.myapplication.utils

import uk.ac.tees.w9623063.myapplication.domain.room.model.ResultEntity
import uk.ac.tees.w9623063.myapplication.domain.network.model.Result

fun List<ResultEntity>.mapToResult(): List<Result> {
    val list = mutableListOf<Result>()
    this.forEach { entity ->
        val result = Result(
            away_team_key = entity.away_team_key,
            away_team_logo = entity.away_team_logo ?: "",
            cards = emptyList(),
            country_logo = entity.country_logo ?: "",
            country_name = entity.country_name,
            event_away_formation = "",
            event_away_team = entity.event_away_team,
            event_country_key = 0,
            event_date = entity.event_date,
            event_final_result = entity.event_final_result,
            event_ft_result = "",
            event_halftime_result = entity.event_halftime_result,
            event_home_formation = "",
            event_home_team = entity.event_home_team,
            event_key = entity.event_key,
            event_live = "",
            event_penalty_result = "",
            event_referee = "",
            event_stadium = "",
            event_status = "",
            event_time = entity.event_time,
            fk_stage_key = 0,
            goalscorers = emptyList(),
            home_team_key = entity.home_team_key,
            home_team_logo = entity.home_team_logo ?: "",
            league_group = "",
            league_key = entity.league_key,
            league_logo = entity.league_logo ?: "",
            league_name = entity.league_name,
            league_round = entity.league_round,
            league_season = entity.league_season,
            stage_name = "",
            statistics = emptyList(),
            substitutes = emptyList()
        )
        list.add(result)
    }
    return list
}

fun List<Result>.mapToResultEntity(): List<ResultEntity> {
    val list = mutableListOf<ResultEntity>()
    this.forEach { result ->
        val entity = ResultEntity(
            away_team_key = result.away_team_key,
            away_team_logo = result.away_team_logo,
            country_logo = result.country_logo,
            country_name = result.country_name,
            event_away_team = result.event_away_team,
            event_date = result.event_date,
            event_final_result = result.event_final_result,
            event_halftime_result = result.event_halftime_result,
            event_home_team = result.event_home_team,
            event_key = result.event_key,
            event_time = result.event_time,
            home_team_key = result.home_team_key,
            home_team_logo = result.home_team_logo,
            league_key = result.league_key,
            league_logo = result.league_logo,
            league_name = result.league_name,
            league_round = result.league_round,
            league_season = result.league_season,
        )
        list.add(entity)
    }
    return list
}