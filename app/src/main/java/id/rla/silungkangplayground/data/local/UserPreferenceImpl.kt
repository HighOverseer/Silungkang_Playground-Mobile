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

    override suspend fun getPhoneNumber(): String {
        val preferences = dataStore.data.first()
        return preferences[PHONE_NUMBER_KEY] ?: ""
    }

    override suspend fun savePhoneNumber(phoneNumber: String) {
        dataStore.edit { preferences ->
            preferences[PHONE_NUMBER_KEY] = phoneNumber
        }
    }

    override suspend fun resetPhoneNumber() {
        dataStore.edit { preferences ->
            preferences[PHONE_NUMBER_KEY] = ""
        }
    }

    companion object{
        const val DATA_STORE_NAME = "user"

        private val TOKEN_KEY = stringPreferencesKey("token")
        private val PHONE_NUMBER_KEY = stringPreferencesKey("memberId")
    }
}