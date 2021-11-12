package ge.nlatsabidze.roomexample.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ApiInstance {

    val API: Api by lazy {
        Retrofit.Builder()
                .baseUrl("https://run.mocky.io/")
                .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()))
                .build()
                .create(Api::class.java)
    }
}
