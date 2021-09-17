package team.mobileb.opgg.util.extension

import com.google.gson.Gson

inline fun <reified T> String.toModel(): T = Gson().fromJson(this, T::class.java)
