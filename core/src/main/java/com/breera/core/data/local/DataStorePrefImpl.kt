package com.breera.core.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import kotlin.reflect.KClass

/**
 * Implementation of the DataStorePref interface using Jetpack DataStore.
 * This class provides methods to store, retrieve, and remove various data types
 * including strings, integers, booleans, and serialized objects.
 */
internal class DataStorePrefImpl(private val dataStore: DataStore<Preferences>) : DataStorePref {

    /**
     * Stores a string value in the DataStore.
     * @param key The key associated with the value.
     * @param value The string value to store.
     */
    override suspend fun putString(key: String, value: String) {
        val preferencesKey = stringPreferencesKey(key)
        dataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }

    /**
     * Stores a list of strings in the DataStore.
     * @param key The key associated with the list.
     * @param value The list of strings to store.
     */
    override suspend fun putListString(key: String, value: MutableList<String>) {
        val preferencesKey = stringSetPreferencesKey(key)
        dataStore.edit { preferences ->
            preferences[preferencesKey] = value.toSet()
        }
    }

    /**
     * Stores an integer value in the DataStore.
     * @param key The key associated with the value.
     * @param value The integer value to store.
     */
    override suspend fun putInt(key: String, value: Int) {
        val preferencesKey = intPreferencesKey(key)
        dataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }

    /**
     * Stores a boolean value in the DataStore.
     * @param key The key associated with the value.
     * @param value The boolean value to store.
     */
    override suspend fun putBoolean(key: String, value: Boolean) {
        val preferencesKey = booleanPreferencesKey(key)
        dataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }

    /**
     * Retrieves a string value from the DataStore.
     * @param key The key associated with the value.
     * @return The string value, or null if not found.
     */
    override suspend fun getString(key: String): String? {
        return try {
            val preferencesKey = stringPreferencesKey(key)
            val preferences = dataStore.data.first()
            preferences[preferencesKey]
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Retrieves a list of strings from the DataStore.
     * @param key The key associated with the list.
     * @return The list of strings, or an empty list if not found.
     */
    override suspend fun getStringList(key: String): MutableList<String> {
        return try {
            val preferencesKey = stringSetPreferencesKey(key)
            val preferences = dataStore.data.first()
            preferences[preferencesKey]?.toMutableList() ?: mutableListOf()
        } catch (e: Exception) {
            e.printStackTrace()
            mutableListOf()
        }
    }

    /**
     * Retrieves an integer value from the DataStore.
     * @param key The key associated with the value.
     * @return The integer value, or null if not found.
     */
    override suspend fun getInt(key: String): Int? {
        return try {
            val preferencesKey = intPreferencesKey(key)
            val preferences = dataStore.data.first()
            preferences[preferencesKey]
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Retrieves a boolean value from the DataStore.
     * @param key The key associated with the value.
     * @return The boolean value, or null if not found.
     */
    override suspend fun getBoolean(key: String): Boolean? {
        return try {
            val preferencesKey = booleanPreferencesKey(key)
            val preferences = dataStore.data.first()
            preferences[preferencesKey]
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Removes a string value from the DataStore.
     * @param key The key associated with the value.
     */
    override suspend fun removeString(key: String) {
        val preferencesKey = stringPreferencesKey(key)
        dataStore.edit { preferences ->
            preferences.remove(preferencesKey)
        }
    }

    /**
     * Removes an integer value from the DataStore.
     * @param key The key associated with the value.
     */
    override suspend fun removeInt(key: String) {
        val preferencesKey = intPreferencesKey(key)
        dataStore.edit { preferences ->
            preferences.remove(preferencesKey)
        }
    }

    /**
     * Removes a boolean value from the DataStore.
     * @param key The key associated with the value.
     */
    override suspend fun removeBoolean(key: String) {
        val preferencesKey = booleanPreferencesKey(key)
        dataStore.edit { preferences ->
            preferences.remove(preferencesKey)
        }
    }

    /**
     * Clears all preferences in the DataStore.
     */
    override suspend fun clearPreferences() {
        dataStore.edit {
            it.clear()
        }
    }

    /**
     * Stores a list of objects in the DataStore as a JSON string.
     * @param key The key associated with the list.
     * @param list The list of objects to store.
     * @param clazz The KClass of the objects being stored.
     */
    @OptIn(InternalSerializationApi::class)
    override suspend fun <T : Any> putObjectList(key: String, list: List<T>, clazz: KClass<T>) {
        dataStore.edit { preferences ->
            val jsonString = Json.encodeToString(ListSerializer(clazz.serializer()), list)
            preferences[stringPreferencesKey(key)] = jsonString
        }
    }

    /**
     * Retrieves a list of objects from the DataStore, deserialized from a JSON string.
     * @param key The key associated with the list.
     * @param clazz The KClass of the objects being retrieved.
     * @return The list of objects, or null if not found.
     */
    @OptIn(InternalSerializationApi::class)
    override suspend fun <T : Any> getObjectList(key: String, clazz: KClass<T>): List<T>? {
        return try {
            val preferencesKey = stringPreferencesKey(key)
            val preferences = dataStore.data.first()
            val jsonString = preferences[preferencesKey] ?: return null
            Json.decodeFromString(ListSerializer(clazz.serializer()), jsonString)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Stores an object in the DataStore as a JSON string.
     * @param key The key associated with the object.
     * @param model The object to store.
     * @param clazz The KClass of the object being stored.
     */
    @OptIn(InternalSerializationApi::class)
    override suspend fun <T : Any> putObject(key: String, model: T, clazz: KClass<T>) {
        dataStore.edit { preferences ->
            val jsonString = Json.encodeToString(clazz.serializer(), model)
            preferences[stringPreferencesKey(key)] = jsonString
        }
    }

    /**
     * Retrieves an object from the DataStore, deserialized from a JSON string.
     * @param key The key associated with the object.
     * @param clazz The KClass of the object being retrieved.
     * @return The object, or null if not found.
     */
    @OptIn(InternalSerializationApi::class)
    override suspend fun <T : Any> getObject(key: String, clazz: KClass<T>): T? {
        return try {
            val preferencesKey = stringPreferencesKey(key)
            val preferences = dataStore.data.first()
            val jsonString = preferences[preferencesKey] ?: return null
            Json.decodeFromString(clazz.serializer(), jsonString)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}