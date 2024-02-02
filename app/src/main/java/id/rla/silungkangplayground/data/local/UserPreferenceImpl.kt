package id.rla.silungkangplayground.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import id.rla.silungkangplayground.domain.data.UserPreference
import kotlinx.coroutines.flow.first

val Context.dataStore : DataStore<Preferences> by preferencesDataStore(UserPreferenceImpl.DATA_STORE_NAME)

class UserPreferenceImpl private constructor(
    private val dataStore:DataStore<Preferences>
) : UserPreference {

    override suspend fun getToken():String{
        val preferences = dataStore.data.first()
        return preferences[TOKEN_KEY] ?: ""
    }

    override suspend fun saveToken(token:String){
        dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    companion object{
        const val DATA_STORE_NAME = "user"

        private val TOKEN_KEY = stringPreferencesKey("token")

        @Volatile
        private var INSTANCE:UserPreferenceImpl?=null

        fun getInstance(dataStore: DataStore<Preferences>):UserPreferenceImpl{
            return INSTANCE?: synchronized(this){
                INSTANCE?:UserPreferenceImpl(dataStore)
            }.also { INSTANCE = it }
        }
    }
}