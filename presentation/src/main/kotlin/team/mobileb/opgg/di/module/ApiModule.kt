package team.mobileb.opgg.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import team.mobileb.opgg.di.qualifier.RoomsApiQualifier
import team.mobileb.opgg.di.qualifier.UtilsApiQualifier
import javax.inject.Singleton
import kotlin.reflect.KClass

@Suppress("HasPlatformType")
@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    private const val BaseUrl = "http://18.189.220.120:8090/api/v1"

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
    @RoomsApiQualifier
    @Singleton
    fun provideRoomRetrofit(loggingInterceptor: HttpLoggingInterceptor) =
        buildRetrofit(loggingInterceptor, "$BaseUrl/rooms", RoomsApiQualifier::class)

    @Provides
    @UtilsApiQualifier
    @Singleton
    fun provideUtilRetrofit(loggingInterceptor: HttpLoggingInterceptor) =
        buildRetrofit(loggingInterceptor, "$BaseUrl/utils", UtilsApiQualifier::class)
}
