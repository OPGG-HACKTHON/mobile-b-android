package team.mobileb.opgg.data.datasource

import team.mobileb.opgg.data.api.UtilsApi
import team.mobileb.opgg.data.api.model.PositionInfoResponse

interface UtilsRemoteDataSource {
    suspend fun retrievePosition(): PositionInfoResponse
}

class UtilsRemoteDataSourceImpl(private val api: UtilsApi) : UtilsRemoteDataSource {
    override suspend fun retrievePosition(): PositionInfoResponse {
        return api.retrievePosition()
    }

}
