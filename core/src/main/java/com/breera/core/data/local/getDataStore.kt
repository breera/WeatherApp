package com.breera.core.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

fun getDataStore(context: Context): DataStore<Preferences> = getDataStore(
    producePath = { context.filesDir.resolve(DataStoreFileName).absolutePath }
)
