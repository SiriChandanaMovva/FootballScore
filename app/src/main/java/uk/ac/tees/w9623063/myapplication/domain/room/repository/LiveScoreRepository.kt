package uk.ac.tees.w9623063.myapplication.domain.room.repository

import uk.ac.tees.w9623063.myapplication.data.room.dao.LiveScoreDao
import uk.ac.tees.w9623063.myapplication.domain.room.model.ResultEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LiveScoreRepository(private val liveScoreDao: LiveScoreDao) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    suspend fun getAllItem(): List<ResultEntity> {
       return withContext(coroutineScope.coroutineContext) {
           liveScoreDao.getAll()
       }
    }

    fun insertItem(item: ResultEntity) {
        coroutineScope.launch(Dispatchers.IO) {
            liveScoreDao.insertData(item)
        }
    }

    fun insertAllItems(items: List<ResultEntity>) {
        coroutineScope.launch(Dispatchers.IO) {
            items.forEach {item ->
                liveScoreDao.insertData(item)
            }
        }
    }

    fun deleteItem(item: ResultEntity) {
        coroutineScope.launch(Dispatchers.IO) {
            liveScoreDao.deleteData(item)
        }
    }

}