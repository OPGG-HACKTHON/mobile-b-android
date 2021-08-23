package team.mobileb.opgg.datasource

import team.mobileb.opgg.api.RoomApi
import team.mobileb.opgg.api.model.RoomInfoResponse
import javax.inject.Inject

interface RoomRemoteDataSource {
    suspend fun createRoom(userKey: String): RoomInfoResponse
    suspend fun getRoomInfo(): RoomInfoResponse
}

class RoomRemoteDataSourceImpl @Inject constructor(private val api: RoomApi) : RoomRemoteDataSource {
    override suspend fun createRoom(userKey: String): RoomInfoResponse {
        return api.createRoom(userKey)
    }
    override suspend fun getRoomInfo(): RoomInfoResponse {
        return api.getRoom()
    }



}