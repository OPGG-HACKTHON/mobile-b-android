package team.mobileb.opgg.domain.usecase

import team.mobileb.opgg.domain.model.CheckInfo
import team.mobileb.opgg.domain.repository.RoomRepository

private typealias BaseCheckRoomUseCase = BaseUseCase<String, CheckInfo>

class CheckRoomUseCase(private val repository: RoomRepository) : BaseCheckRoomUseCase {
    override suspend fun invoke(parameter: String) = repository.checkRoom(inviteCode = parameter)
}
