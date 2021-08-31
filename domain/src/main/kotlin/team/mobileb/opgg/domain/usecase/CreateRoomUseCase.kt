package team.mobileb.opgg.domain.usecase

import team.mobileb.opgg.domain.model.RoomInfo
import team.mobileb.opgg.domain.repository.RoomRepository

private typealias BaseCreateRoomUseCase = BaseUseCase<String, RoomInfo>

class CreateRoomUseCase(private val repository: RoomRepository) : BaseCreateRoomUseCase {
    override suspend fun invoke(parameter: String) = repository.createRoom(userKey = parameter)
}
