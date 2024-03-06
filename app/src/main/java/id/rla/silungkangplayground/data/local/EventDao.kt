package id.rla.silungkangplayground.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EventDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event:List<EventEntity>)

    /*@Query("SELECT * FROM event")
    fun getAllEvent():PagingSource<Int, EventEntity>*/
    @Query("SELECT * FROM event")
    fun getAllEvent():PagingSource<Int, EventEntity>

    @Query("DELETE FROM EVENT")
    suspend fun deleteAllEvent()
}