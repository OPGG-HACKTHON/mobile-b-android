package team.mobileb.opgg.domain.usecase

import team.mobileb.opgg.domain.model.PositionInfo
import team.mobileb.opgg.domain.repository.UtilsRepository

class RetrieveUtilsUseCase(private val repository: UtilsRepository) {
    suspend fun retrieve(): PositionInfo {
        return repository.retrievePosition()
    }
}
