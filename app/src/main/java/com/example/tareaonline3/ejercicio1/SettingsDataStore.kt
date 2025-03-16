package tareaOnline3.ejercicio1

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsDataStore(context: Context) {

    private val dataStore = context.dataStore

    companion object {
        val BACKGROUND_COLOR = stringPreferencesKey("background_color")
        val CONVERSION_RATE = doublePreferencesKey("conversion_rate")
    }

    //-------funcion guardarBackgroundColor-------
    suspend fun guardarBackgroundColor(color: String) {
        dataStore.edit { preferences ->
            preferences[BACKGROUND_COLOR] = color
        }
    }

    //-------funcion guardarRateConversion-------
    suspend fun guardarRateConversion(rate: Double) {
        dataStore.edit { preferences ->
            preferences[CONVERSION_RATE] = rate
        }
    }

    val backgroundColor: Flow<String> = dataStore.data
        .map { preferences ->
            preferences[BACKGROUND_COLOR] ?: "blue"
        }

    val conversionRate: Flow<Double> = dataStore.data
        .map { preferences ->
            preferences[CONVERSION_RATE] ?: 1.1
        }
}