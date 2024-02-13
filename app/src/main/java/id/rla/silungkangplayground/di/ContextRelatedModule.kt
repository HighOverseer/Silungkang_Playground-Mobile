package id.rla.silungkangplayground.di

import android.content.Context
import android.util.DisplayMetrics
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.rla.silungkangplayground.data.local.dataStore
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ContextRelatedModule {
    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext context: Context):DataStore<Preferences> = context.dataStore
    @Provides
    fun provideDisplayMetrics(@ApplicationContext context: Context):DisplayMetrics = context.resources.displayMetrics

}