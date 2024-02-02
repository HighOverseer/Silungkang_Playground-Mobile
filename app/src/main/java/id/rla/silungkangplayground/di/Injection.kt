package id.rla.silungkangplayground.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import id.rla.silungkangplayground.data.RepositoryImpl
import id.rla.silungkangplayground.data.local.UserPreferenceImpl
import id.rla.silungkangplayground.data.remote.network.ApiClient
import id.rla.silungkangplayground.domain.data.Repository

object Injection {

    fun provideRepository(dataStore: DataStore<Preferences>):Repository{
        val userPreference = UserPreferenceImpl.getInstance(dataStore)
        val apiService = ApiClient.getApiService(userPreference)
        return RepositoryImpl.getInstance(apiService, userPreference)
    }
}