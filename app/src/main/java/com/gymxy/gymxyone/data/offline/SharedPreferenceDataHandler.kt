package com.gymxy.gymxyone.data.offline

import android.content.SharedPreferences
import javax.inject.Inject

class SharedPreferenceDataHandler @Inject constructor(
    private val sharedPreferences: SharedPreferences
){
    fun saveUid( uid: String){
        sharedPreferences.edit().putString("UserID", uid).apply()
    }
    fun getUid(): String? {
        return sharedPreferences.getString("UserID", null)
    }
    fun saveName( name: String){
        sharedPreferences.edit().putString("Name", name).apply()
    }
    fun getName(): String? {
        return sharedPreferences.getString("Name", null)
    }
    fun savePhoto( photo: String){
        sharedPreferences.edit().putString("Photo", photo).apply()
    }
    fun getPhoto(): String? {
        return sharedPreferences.getString("Photo", null)
    }
    fun clearData(){
        sharedPreferences.edit().clear().apply()
    }
}