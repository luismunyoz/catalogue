package com.luismunyoz.catalogue.data

import retrofit2.Call

inline fun <T, U> Call<T>.unwrapCall(f: T.() -> U) = execute().body()?.f()