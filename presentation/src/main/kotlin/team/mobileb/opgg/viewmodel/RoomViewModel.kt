package team.mobileb.opgg.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import team.mobileb.opgg.api.RoomApi
import team.mobileb.opgg.api.model.RoomInfoResponse
import team.mobileb.opgg.datasource.RoomRemoteDataSourceImpl
import team.mobileb.opgg.model.RoomInfo
import team.mobileb.opgg.repository.RoomRepositoryImpl
import team.mobileb.opgg.usecase.CreateRoomUseCase
import team.mobileb.opgg.usecase.GetRoomUseCase
import java.util.concurrent.TimeUnit

class RoomViewModel(
    private val createRoomUseCase: CreateRoomUseCase,
    private val getRoomUseCase: GetRoomUseCase
) : ViewModel() {

    private val _createRoomInfo = MutableLiveData<RoomInfo>()
    val createRoomInfo : LiveData<RoomInfo> get() =  _createRoomInfo
    private val _roomInfo = MutableLiveData<RoomInfo>()
    val roomInfo : LiveData<RoomInfo> get() =  _roomInfo
    fun createRoom(userKey: String) {
        viewModelScope.launch {
            _createRoomInfo.value = createRoomUseCase.execute(userKey)
        }
    }

    fun getRoomInfo(userKey: String) {
        viewModelScope.launch {
            _roomInfo.value = getRoomUseCase.invoke(userKey)
        }

    }

}