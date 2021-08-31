package team.mobileb.opgg.domain.usecase

import team.mobileb.opgg.domain.model.CheckInfo
import team.mobileb.opgg.data.repository.RoomRepository

class CheckRoomUseCase(private val repository: RoomRepository) {
    suspend fun check(inviteCode: String): CheckInfo {
        return repository.checkRoom(inviteCode)
    }
}