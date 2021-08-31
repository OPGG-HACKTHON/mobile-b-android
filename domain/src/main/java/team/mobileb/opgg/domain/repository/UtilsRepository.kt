package team.mobileb.opgg.domain.repository

import team.mobileb.opgg.domain.RequestResult
import team.mobileb.opgg.domain.model.PositionInfo

interface UtilsRepository {
    suspend fun retrievePosition(): RequestResult<PositionInfo>
}
