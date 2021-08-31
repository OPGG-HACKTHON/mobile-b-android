package team.mobileb.opgg.domain.usecase

import team.mobileb.opgg.domain.model.RoomInfo
import team.mobileb.opgg.data.repository.RoomRepository

class CreateRoomUseCase(private val repository: RoomRepository) {
    suspend fun execute(userKey: String): RoomInfo {
        return repository.createRoom(userKey)
    }

}