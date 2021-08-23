package team.mobileb.opgg.repository

import team.mobileb.opgg.datasource.RoomRemoteDataSource
import team.mobileb.opgg.model.RoomInfo
import javax.inject.Inject

class RoomRepositoryImpl @Inject constructor(private val dataSource: RoomRemoteDataSource) : RoomRepository{

    override suspend fun createRoom(userKey: String): RoomInfo {
        return dataSource.createRoom(userKey)
    }

    override suspend fun getRoomInfo(userKey: String): RoomInfo {
        TODO("Not yet implemented")
    }
}