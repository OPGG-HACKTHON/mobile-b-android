package team.mobileb.opgg.usecase

import team.mobileb.opgg.model.RoomInfo
import team.mobileb.opgg.repository.RoomRepository

class RetrieveRoomUseCase(private val repository: RoomRepository) {
    suspend fun retrieve(userKey: String): RoomInfo {
        return repository.retrieveRoom(userKey)
    }
}