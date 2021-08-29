package team.mobileb.opgg.datasource

import team.mobileb.opgg.api.UtilsApi
import team.mobileb.opgg.api.model.PositionInfoResponse

interface UtilsRemoteDataSource {

    suspend fun retrievePosition(): PositionInfoResponse

}

class UtilsRemoteDataSourceImpl(private val api : UtilsApi) : UtilsRemoteDataSource {
    override suspend fun retrievePosition(): PositionInfoResponse {
        return api.retrievePosition()
    }

}
