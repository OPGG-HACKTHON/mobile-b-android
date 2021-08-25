package team.mobileb.opgg.datasource

import team.mobileb.opgg.api.RoomApi
import team.mobileb.opgg.api.model.CheckInfoResponse
import team.mobileb.opgg.api.model.RoomInfoResponse

interface RoomRemoteDataSource {
    suspend fun createRoom(userKey: String): RoomInfoResponse
    suspend fun retrieveRoom(userKey:String): RoomInfoResponse
    suspend fun checkRoom(inviteCode : String) : CheckInfoResponse
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