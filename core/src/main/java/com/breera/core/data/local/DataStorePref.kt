package com.breera.core.data.local

import kotlin.reflect.KClass

/**
 * The DataStorePref interface defines a contract for managing local data storage.
 * It provides methods to store, retrieve, and remove various types of data,
 * including primitives, lists, and objects, using a key-value mechanism.
 */
interface DataStorePref {

    /**
     * Stores a string value associated with the given key.
     * @param key The unique identifier for the value.
     * @param value The string value to store.
     */
    suspend fun putString(key: String, value: String)

    /**
     * Stores a list of strings associated with the given key.
     * @param key The unique identifier for the list.
     * @param value The list of strings to store.
     */
    suspend fun putListString(key: String, value: MutableList<String>)

    /**
     * Stores an integer value associated with the given key.
     * @param key The unique identifier for the value.
     * @param value The integer value to store.
     */
    suspend fun putInt(key: String, value: Int)

    /**
     * Stores a boolean value associated with the given key.
     * @param key The unique identifier for the value.
     * @param value The boolean value to store.
     */
    suspend fun putBoolean(key: String, value: Boolean)

    /**
     * Retrieves a string value associated with the given key.
     * @param key The unique identifier for the value.
     * @return The string value, or null if not found.
     */
    suspend fun getString(key: String): String?

    /**
     * Retrieves a list of strings associated with the given key.
     * @param key The unique identifier for the list.
     * @return The list of strings, or an empty list if not found.
     */
    suspend fun getStringList(key: String): MutableList<String>

    /**
     * Retrieves an integer value associated with the given key.
     * @param key The unique identifier for the value.
     * @return The integer value, or null if not found.
     */
    suspend fun getInt(key: String): Int?

    /**
     * Retrieves a boolean value associated with the given key.
     * @param key The unique identifier for the value.
     * @return The boolean value, or null if not found.
     */
    suspend fun getBoolean(key: String): Boolean?

    /**
     * Removes a string value associated with the given key.
     * @param key The unique identifier for the value.
     */
    suspend fun removeString(key: String)

    /**
     * Removes an integer value associated with the given key.
     * @param key The unique identifier for the value.
     */
    suspend fun removeInt(key: String)

    /**
     * Removes a boolean value associated with the given key.
     * @param key The unique identifier for the value.
     */
    suspend fun removeBoolean(key: String)

    /**
     * Clears all stored preferences.
     */
    suspend fun clearPreferences()

    /**
     * Stores a list of objects of type T associated with the given key.
     * @param key The unique identifier for the list.
     * @param list The list of objects to store.
     * @param clazz The KClass of the objects being stored.
     */
    suspend fun <T : Any> putObjectList(key: String, list: List<T>, clazz: KClass<T>)

    /**
     * Retrieves a list of objects of type T associated with the given key.
     * @param key The unique identifier for the list.
     * @param clazz The KClass of the objects being retrieved.
     * @return The list of objects, or null if not found.
     */
    suspend fun <T : Any> getObjectList(key: String, clazz: KClass<T>): List<T>?

    /**
     * Stores an object of type T associated with the given key.
     * @param key The unique identifier for the object.
     * @param model The object to store.
     * @param clazz The KClass of the object being stored.
     */
    suspend fun <T : Any> putObject(key: String, model: T, clazz: KClass<T>)

    /**
     * Retrieves an object of type T associated with the given key.
     * @param key The unique identifier for the object.
     * @param clazz The KClass of the object being retrieved.
     * @return The object, or null if not found.
     */
    suspend fun <T : Any> getObject(key: String, clazz: KClass<T>): T?
}