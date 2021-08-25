package team.mobileb.opgg.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import team.mobileb.opgg.api.RoomApi
import team.mobileb.opgg.datasource.RoomRemoteDataSourceImpl
import team.mobileb.opgg.model.CheckInfo
import team.mobileb.opgg.model.RoomInfo
import team.mobileb.opgg.repository.RoomRepositoryImpl
import team.mobileb.opgg.usecase.CheckRoomUseCase
import team.mobileb.opgg.usecase.CreateRoomUseCase
import team.mobileb.opgg.usecase.RetrieveRoomUseCase

class RoomViewModel : ViewModel() {

    private val localhost = "192.168.1.104" // 실행 시 자신의 ip로 바꾸기  이식: 192.168.1.104

    private val httpLoggingInterceptor = HttpLoggingInterceptor()
    private val retrofit = Retrofit.Builder()
    private val request = buildRetrofit(
        retrofit,
        RoomApi::class.java
    )

    private val dataSource = RoomRemoteDataSourceImpl(request)
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
    private fun getInterceptor(interceptor: Interceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(interceptor)
        return builder.build()
    }
    private fun <T> buildRetrofit(retrofit: Retrofit.Builder, service: Class<T>) =
        retrofit.client(getInterceptor(httpLoggingInterceptor))
            .baseUrl("https://$localhost:8080/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(service)
}