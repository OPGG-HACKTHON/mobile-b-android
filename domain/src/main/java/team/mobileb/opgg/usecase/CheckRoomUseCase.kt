package team.mobileb.opgg.usecase

import team.mobileb.opgg.model.CheckInfo
import team.mobileb.opgg.repository.RoomRepository

class CheckRoomUseCase(private val repository: RoomRepository) {
    suspend fun check(inviteCode : String) : CheckInfo {
        return repository.checkRoom(inviteCode)
    }
}