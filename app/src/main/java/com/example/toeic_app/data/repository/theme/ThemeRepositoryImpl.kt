package com.example.toeic_app.data.repository.theme

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.toeic_app.domain.repository.ThemeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


private val Context.dataStore by preferencesDataStore(name = "theme_prefs")
class ThemeRepositoryImpl(
    private val context: Context
) : ThemeRepository{
    private  val IS_LIGHT_MODE  = booleanPreferencesKey("is_light_mode")
    override fun getThemeMode(): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[IS_LIGHT_MODE]?: true
        }
    }

    override suspend fun setThemeMode(isLightMode: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_LIGHT_MODE] = isLightMode
        }
    }
}