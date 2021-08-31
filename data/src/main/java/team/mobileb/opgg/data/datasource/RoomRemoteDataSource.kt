package team.mobileb.opgg.data.datasource

import team.mobileb.opgg.data.api.RoomApi
import team.mobileb.opgg.data.api.model.CheckInfoResponse
import team.mobileb.opgg.data.api.model.RoomInfoResponse

interface RoomRemoteDataSource {
    suspend fun createRoom(userKey: String): RoomInfoResponse
    suspend fun retrieveRoom(userKey: String): RoomInfoResponse
    suspend fun checkRoom(inviteCode: String): CheckInfoResponse
}

class RoomRemoteDataSourceImpl(private val api: RoomApi) : RoomRemoteDataSource {
    override suspend fun createRoom(userKey: String): RoomInfoResponse {
        return api.createRoom(userKey)
    }


    override suspend fun retrieveRoom(userKey: String): RoomInfoResponse {
        return api.retrieveRoom(userKey)
    }

    override suspend fun checkRoom(inviteCode: String): CheckInfoResponse {
        return api.checkRoom(inviteCode)
    }


}