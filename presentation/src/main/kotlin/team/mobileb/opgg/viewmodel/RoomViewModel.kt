package team.mobileb.opgg.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import team.mobileb.opgg.data.api.RoomApi
import team.mobileb.opgg.data.datasource.RoomRepositoryImpl
import team.mobileb.opgg.domain.model.CheckInfo
import team.mobileb.opgg.domain.model.RoomInfo
import team.mobileb.opgg.domain.usecase.CheckRoomUseCase
import team.mobileb.opgg.domain.usecase.CreateRoomUseCase
import team.mobileb.opgg.domain.usecase.RetrieveRoomUseCase

class RoomViewModel : ViewModel() {

    private val localhost = "18.189.220.120"

    private val httpLoggingInterceptor = HttpLoggingInterceptor()
    private val retrofit = Retrofit.Builder()
    private val request = buildRetrofit(
        retrofit,
        RoomApi::class.java
    )

    private val dataSource = RoomRepositoryImpl(request)
    private val repositoryImpl = RoomRepositoryImpl(dataSource)
    private val createRoomUseCase = CreateRoomUseCase(repositoryImpl)
    private val retrieveRoomUseCase = RetrieveRoomUseCase(repositoryImpl)
    private val checkRoomUseCase = CheckRoomUseCase(repositoryImpl)

    private val _createRoomInfo = MutableLiveData<RoomInfo>()
    val createRoomInfo: LiveData<RoomInfo> get() = _createRoomInfo
    private val _retrieveRoomInfo = MutableLiveData<RoomInfo>()
    val retieveRoomInfo: LiveData<RoomInfo> get() = _retrieveRoomInfo
    private val _checkRoomInfo = MutableLiveData<CheckInfo>()
    val checkRoomInfo: LiveData<CheckInfo> get() = _checkRoomInfo

    fun createRoom(userKey: String) {
        viewModelScope.launch {
            _createRoomInfo.value = createRoomUseCase.execute(userKey)
        }
    }

    fun retrieveRoom(userKey: String) {
        viewModelScope.launch {
            _retrieveRoomInfo.value = retrieveRoomUseCase.retrieve(userKey)
        }
    }

    fun checkRoom(inviteCode: String) {
        viewModelScope.launch {
            _checkRoomInfo.value = checkRoomUseCase.check(inviteCode)
        }
    }

    private fun getInterceptor(interceptor: HttpLoggingInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(interceptor)

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            .setLevel(HttpLoggingInterceptor.Level.BASIC)
            .setLevel(HttpLoggingInterceptor.Level.HEADERS)
        return builder.build()
    }

    private fun <T> buildRetrofit(retrofit: Retrofit.Builder, service: Class<T>) =
        retrofit.client(getInterceptor(httpLoggingInterceptor))
            .baseUrl("http://$localhost:8090/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(service)
}