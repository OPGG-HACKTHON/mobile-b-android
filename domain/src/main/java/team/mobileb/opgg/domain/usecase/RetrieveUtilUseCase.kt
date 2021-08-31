package team.mobileb.opgg.domain.usecase

import team.mobileb.opgg.domain.model.PositionInfo
import team.mobileb.opgg.domain.repository.UtilRepository

private typealias BaseRetrieveUtilUseCase = BaseUseCaseWithoutParameter<PositionInfo>

class RetrieveUtilUseCase(private val repository: UtilRepository) : BaseRetrieveUtilUseCase {
    override suspend fun invoke() = repository.retrievePosition()
}
