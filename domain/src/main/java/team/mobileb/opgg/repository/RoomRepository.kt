package team.mobileb.opgg.repository

import team.mobileb.opgg.model.CheckInfo
import team.mobileb.opgg.model.RoomInfo

interface RoomRepository {
    suspend fun createRoom(userKey : String) : RoomInfo
    suspend fun retrieveRoom(userKey: String) : RoomInfo
    suspend fun checkRoom(inviteCode : String) : CheckInfo
}