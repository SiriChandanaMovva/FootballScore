package uk.ac.tees.w9623063.myapplication.domain.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "results")
data class ResultEntity(
    @ColumnInfo
    var away_team_key: Int = 0,
    @ColumnInfo
    var away_team_logo: String?,
    @ColumnInfo
    var country_logo: String?,
    @ColumnInfo
    var country_name: String = "",
    @ColumnInfo
    var event_away_team: String = "",
    @ColumnInfo
    var event_date: String = "",
    @ColumnInfo
    var event_final_result: String = "",
    @ColumnInfo
    var event_halftime_result: String = "",
    @ColumnInfo
    var event_home_team: String = "",
    @PrimaryKey @ColumnInfo
    var event_key: Int = 0,
    @ColumnInfo
    var event_time: String = "",
    @ColumnInfo
    var home_team_key: Int = 0,
    @ColumnInfo
    var home_team_logo: String?,
    @ColumnInfo
    var league_key: Int = 0,
    @ColumnInfo
    var league_logo: String?,
    @ColumnInfo
    var league_name: String = "",
    @ColumnInfo
    var league_round: String = "",
    @ColumnInfo
    var league_season: String = ""
)
