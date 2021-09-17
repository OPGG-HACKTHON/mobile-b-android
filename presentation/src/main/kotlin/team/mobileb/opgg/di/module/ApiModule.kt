package team.mobileb.opgg.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import team.mobileb.opgg.data.api.RoomsApi
import team.mobileb.opgg.data.api.UtilsApi
import kotlin.reflect.KClass

@Module
@InstallIn(ViewModelComponent::class)
object ApiModule {
    private const val BaseUrl = "http://gdok.site:3724/api/v1"

    private fun getInterceptor(vararg interceptors: Interceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
        for (interceptor in interceptors) builder.addInterceptor(interceptor)
        return builder.build()
    }

    private fun <T : Any> buildRetrofit(
        loggingInterceptor: HttpLoggingInterceptor,
        baseUrl: String,
        service: KClass<T>,
    ) = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(getInterceptor(loggingInterceptor))
        .build()
        .create(service.java)

    @Provides
    @ViewModelScoped
    fun provideRoomApi(loggingInterceptor: HttpLoggingInterceptor): RoomsApi =
        buildRetrofit(loggingInterceptor, "$BaseUrl/rooms/", RoomsApi::class)

    @Provides
    @ViewModelScoped
    fun provideUtilApi(loggingInterceptor: HttpLoggingInterceptor): UtilsApi =
        buildRetrofit(loggingInterceptor, "$BaseUrl/utils/", UtilsApi::class)
}
