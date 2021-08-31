package team.mobileb.opgg.data.util

import retrofit2.Response

fun <T> Response<T>.isValid() = isSuccessful && body() != null
