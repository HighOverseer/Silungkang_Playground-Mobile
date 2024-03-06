package id.rla.silungkangplayground.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.rla.silungkangplayground.data.local.EventDao
import id.rla.silungkangplayground.data.local.EventDatabase
import id.rla.silungkangplayground.data.local.RemoteKeysDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideEventDatabse(@ApplicationContext applicationContext: Context):EventDatabase{
        return Room.databaseBuilder(
            applicationContext,
            EventDatabase::class.java,
            "event.db",
        ).fallbackToDestructiveMigration()
            .build()
    }



    /*@Provides
    fun provideEventDao(eventDatabase: EventDatabase):EventDao = eventDatabase.eventDao()

    @Provides
    fun provideRemoteKeysDao(eventDatabase: EventDatabase):RemoteKeysDao = eventDatabase.remoteKeysDao()*/

}