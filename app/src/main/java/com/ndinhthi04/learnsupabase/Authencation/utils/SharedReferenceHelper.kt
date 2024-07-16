package com.ndinhthi04.learnsupabase.Authencation.utils

import android.content.Context

class SharedReferenceHelper(private val context: Context) {

    // Companion object là nơi để định nghĩa các thành viên tĩnh.
    companion object {
        // MY_PREF_KEY là một hằng số tĩnh, chỉ có thể truy cập qua lớp mà không cần tạo một đối tượng.
        private const val MY_PREF_KEY = "MY_PREF"
    }

    // Hàm này dùng để lưu trữ chuỗi dữ liệu vào SharedPreferences.
    fun saveStringData(key: String, data: String?) {
        val sharedReferences = context.getSharedPreferences(MY_PREF_KEY, Context.MODE_PRIVATE)
        sharedReferences.edit().putString(key, data).apply()
    }

    // Hàm này dùng để lấy chuỗi dữ liệu từ SharedPreferences.
    fun getStringData(key: String): String? {
        val sharedReferences = context.getSharedPreferences(MY_PREF_KEY, Context.MODE_PRIVATE)
        return sharedReferences.getString(key, null)
    }

    // Hàm này dùng để xóa toàn bộ dữ liệu trong SharedPreferences.
    fun clearPreference(){
        val sharedReferences = context.getSharedPreferences(MY_PREF_KEY, Context.MODE_PRIVATE)
        sharedReferences.edit().clear().apply()
    }
}
