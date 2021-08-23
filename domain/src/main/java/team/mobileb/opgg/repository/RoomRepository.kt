package team.mobileb.opgg.repository

import team.mobileb.opgg.model.RoomInfo

interface RoomRepository {

    suspend fun createRoom(userKey : String) : RoomInfo

    suspend fun getRoomInfo(userKey : String) : RoomInfo
}