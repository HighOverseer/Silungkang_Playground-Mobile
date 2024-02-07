package id.rla.silungkangplayground.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import id.rla.silungkangplayground.domain.data.UserPreference
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore : DataStore<Preferences> by preferencesDataStore(UserPreferenceImpl.DATA_STORE_NAME)

@Singleton
class UserPreferenceImpl @Inject constructor(
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

    override suspend fun resetToken() {
        dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = ""
        }
    }

    override suspend fun getMemberId(): String {
        val preferences = dataStore.data.first()
        return preferences[MEMBER_ID_KEY] ?: ""
    }

    override suspend fun saveMemberId(memberId: String) {
        dataStore.edit { preferences ->
            preferences[MEMBER_ID_KEY] = memberId
        }
    }

    override suspend fun resetMemberId() {
        dataStore.edit { preferences ->
            preferences[MEMBER_ID_KEY] = ""
        }
    }

    companion object{
        const val DATA_STORE_NAME = "user"

        private val TOKEN_KEY = stringPreferencesKey("token")
        private val MEMBER_ID_KEY = stringPreferencesKey("memberId")

        @Volatile
        private var INSTANCE:UserPreferenceImpl?=null

        fun getInstance(dataStore: DataStore<Preferences>):UserPreferenceImpl{
            return INSTANCE?: synchronized(this){
                INSTANCE?:UserPreferenceImpl(dataStore)
            }.also { INSTANCE = it }
        }
    }
}