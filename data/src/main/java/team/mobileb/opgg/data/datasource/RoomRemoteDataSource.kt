package team.mobileb.opgg.data.datasource

import kotlinx.coroutines.flow.Flow
import team.mobileb.opgg.domain.RequestResult
import team.mobileb.opgg.domain.model.CheckInfo
import team.mobileb.opgg.domain.model.RoomInfo

interface RoomRemoteDataSource {
    suspend fun createRoom(userKey: String): Flow<RequestResult<RoomInfo>>
    suspend fun retrieveRoom(userKey: String): Flow<RequestResult<RoomInfo>>
    suspend fun checkRoom(inviteCode: String): Flow<RequestResult<CheckInfo>>
}
