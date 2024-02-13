package id.rla.silungkangplayground.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
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

    override suspend fun getPhoneId(): Int {
        val preferences = dataStore.data.first()
        return preferences[PHONE_ID_KEY] ?: -1
    }


    override suspend fun savePhoneId(phoneId: Int) {
        dataStore.edit { preferences ->
            preferences[PHONE_ID_KEY] = phoneId
        }
    }

    override suspend fun resetPhoneId() {
        dataStore.edit { preferences ->
            preferences[PHONE_ID_KEY] = -1
        }
    }

    companion object{
        const val DATA_STORE_NAME = "user"

        private val TOKEN_KEY = stringPreferencesKey("token")
        private val PHONE_ID_KEY = intPreferencesKey("phone_id")
    }
}