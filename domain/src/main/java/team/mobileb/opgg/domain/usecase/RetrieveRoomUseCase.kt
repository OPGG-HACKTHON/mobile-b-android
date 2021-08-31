package team.mobileb.opgg.domain.usecase

import team.mobileb.opgg.domain.model.RoomInfo
import team.mobileb.opgg.data.repository.RoomRepository

class RetrieveRoomUseCase(private val repository: RoomRepository) {
    suspend fun retrieve(userKey: String): RoomInfo {
        return repository.retrieveRoom(userKey)
    }
}