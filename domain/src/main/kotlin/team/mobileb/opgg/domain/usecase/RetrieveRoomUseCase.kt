package team.mobileb.opgg.domain.usecase

import team.mobileb.opgg.domain.model.RoomInfo
import team.mobileb.opgg.domain.repository.RoomRepository

private typealias BaseRetrieveRoomUseCase = BaseUseCase<String, RoomInfo>

class RetrieveRoomUseCase(private val repository: RoomRepository) : BaseRetrieveRoomUseCase {
    override suspend fun invoke(parameter: String) = repository.retrieveRoom(userKey = parameter)
}
