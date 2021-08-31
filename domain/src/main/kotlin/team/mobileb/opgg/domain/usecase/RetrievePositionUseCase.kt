package team.mobileb.opgg.domain.usecase

import team.mobileb.opgg.domain.model.PositionInfo
import team.mobileb.opgg.domain.repository.UtilsRepository

private typealias BaseRetrieveUtilUseCase = BaseUseCaseWithoutParameter<PositionInfo>

class RetrievePositionUseCase(private val repository: UtilsRepository) : BaseRetrieveUtilUseCase {
    override suspend fun invoke() = repository.retrievePosition()
}
