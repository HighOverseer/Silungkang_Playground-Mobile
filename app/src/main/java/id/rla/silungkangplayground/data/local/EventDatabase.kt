package id.rla.silungkangplayground.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [EventEntity::class, RemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class EventDatabase:RoomDatabase() {
    abstract fun eventDao():EventDao
    abstract fun remoteKeysDao():RemoteKeysDao
}