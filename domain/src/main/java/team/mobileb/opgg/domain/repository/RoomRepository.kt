package team.mobileb.opgg.domain.repository

import team.mobileb.opgg.domain.RequestResult
import team.mobileb.opgg.domain.model.CheckInfo
import team.mobileb.opgg.domain.model.RoomInfo

interface RoomRepository {
    suspend fun createRoom(userKey: String): RequestResult<RoomInfo>
    suspend fun retrieveRoom(userKey: String): RequestResult<RoomInfo>
    suspend fun checkRoom(inviteCode: String): RequestResult<CheckInfo>
}
