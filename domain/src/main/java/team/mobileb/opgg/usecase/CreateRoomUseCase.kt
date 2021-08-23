package team.mobileb.opgg.usecase

import team.mobileb.opgg.model.RoomInfo
import team.mobileb.opgg.repository.RoomRepository

class CreateRoomUseCase(private val repository : RoomRepository) {

    suspend fun execute(userKey : String) : RoomInfo {
        return repository.createRoom(userKey)
    }

}