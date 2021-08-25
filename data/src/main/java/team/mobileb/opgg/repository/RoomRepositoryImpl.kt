package team.mobileb.opgg.repository

import team.mobileb.opgg.datasource.RoomRemoteDataSource
import team.mobileb.opgg.mapper.RoomMapper
import team.mobileb.opgg.model.CheckInfo
import team.mobileb.opgg.model.RoomInfo

// todo : singleton?
class RoomRepositoryImpl(
    private val dataSource: RoomRemoteDataSource
) : RoomRepository {
    override suspend fun createRoom(userKey: String): RoomInfo {
        return RoomMapper.mapperToRoomInfo(dataSource.createRoom(userKey))
            //request.createRoom(userKey)
    }

    override suspend fun retrieveRoom(userKey: String): RoomInfo {
        return RoomMapper.mapperToRoomInfo(dataSource.retrieveRoom(userKey))
            //request.retrieveRoom(userKey)
    }

    override suspend fun checkRoom(inviteCode: String): CheckInfo {
        return RoomMapper.mapperToCheckInfo(dataSource.checkRoom(inviteCode))
    }

}