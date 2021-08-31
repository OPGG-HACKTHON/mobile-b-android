package team.mobileb.opgg.data.datasource

import kotlinx.coroutines.flow.Flow
import team.mobileb.opgg.domain.RequestResult
import team.mobileb.opgg.domain.model.PositionInfo

interface UtilsRemoteDataSource {
    suspend fun retrievePosition(): Flow<RequestResult<PositionInfo>>
}
