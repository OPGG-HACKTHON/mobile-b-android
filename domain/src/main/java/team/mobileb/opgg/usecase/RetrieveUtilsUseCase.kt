package team.mobileb.opgg.usecase

import team.mobileb.opgg.model.PositionInfo
import team.mobileb.opgg.repository.UtilsRepository

class RetrieveUtilsUseCase(private val repository: UtilsRepository) {
    suspend fun retrieve() : PositionInfo{
        return repository.retirevePosition()
    }
}