package team.mobileb.opgg.domain.repository

import kotlinx.coroutines.flow.Flow
import team.mobileb.opgg.domain.RequestResult
import team.mobileb.opgg.domain.model.PositionInfo

interface UtilsRepository {
    suspend fun retrievePosition(): Flow<RequestResult<PositionInfo>>
}
