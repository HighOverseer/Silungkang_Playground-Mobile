package id.rla.silungkangplayground.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.rla.silungkangplayground.data.local.UserPreferenceImpl
import id.rla.silungkangplayground.data.local.dataStore
import id.rla.silungkangplayground.domain.data.UserPreference
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataModule {
    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext context: Context):DataStore<Preferences> = context.dataStore

}