package team.mobileb.opgg.data.repository

import team.mobileb.opgg.data.datasource.UtilsRemoteDataSource
import team.mobileb.opgg.data.mapper.UtilsMapper
import team.mobileb.opgg.domain.model.PositionInfo

class UtilsRepositoryImpl(
    private val dataSource: UtilsRemoteDataSource
) : UtilsRepository {
    override suspend fun retirevePosition(): PositionInfo {
        return UtilsMapper.toPositionInfo(dataSource.retrievePosition())
    }
}