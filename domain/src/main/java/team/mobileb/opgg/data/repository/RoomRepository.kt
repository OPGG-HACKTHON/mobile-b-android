package team.mobileb.opgg.data.repository

import team.mobileb.opgg.domain.model.CheckInfo
import team.mobileb.opgg.domain.model.RoomInfo

interface RoomRepository {
    suspend fun createRoom(userKey: String): RoomInfo
    suspend fun retrieveRoom(userKey: String): RoomInfo
    suspend fun checkRoom(inviteCode: String): CheckInfo
}