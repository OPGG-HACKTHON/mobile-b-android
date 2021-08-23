package team.mobileb.opgg.usecase

import team.mobileb.opgg.model.RoomInfo
import team.mobileb.opgg.repository.RoomRepository

class GetRoomUseCase(private val repository: RoomRepository) {
    suspend fun invoke(userKey : String): RoomInfo {
        return repository.getRoomInfo(userKey)
    }
}