package uk.ac.tees.w9623063.myapplication.data.room.dao

import androidx.room.*
import uk.ac.tees.w9623063.myapplication.domain.room.model.ResultEntity

@Dao
interface LiveScoreDao {
    @Query("SELECT * FROM results")
    fun getAll(): List<ResultEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(item: ResultEntity)

    @Delete
    fun deleteData(item: ResultEntity)

}