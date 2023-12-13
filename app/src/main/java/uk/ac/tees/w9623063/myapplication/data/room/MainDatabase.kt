package uk.ac.tees.w9623063.myapplication.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uk.ac.tees.w9623063.myapplication.data.room.dao.LiveScoreDao
import uk.ac.tees.w9623063.myapplication.domain.room.model.ResultEntity

@Database(entities = [ResultEntity::class], version = 1)
abstract class MainDatabase : RoomDatabase() {
    abstract fun liveScoreDao(): LiveScoreDao

    companion object {
        fun getDB(context: Context): MainDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                MainDatabase::class.java, "main-db"
            ).build()
        }
    }
}
