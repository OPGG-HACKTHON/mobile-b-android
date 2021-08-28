package team.mobileb.opgg.repository

import team.mobileb.opgg.datasource.UtilsRemoteDataSource
import team.mobileb.opgg.mapper.UtilsMapper
import team.mobileb.opgg.model.PositionInfo

class UtilsRepositoryImpl(
    private val dataSource : UtilsRemoteDataSource
) : UtilsRepository{
    override suspend fun retirevePosition(): PositionInfo {
        return UtilsMapper.toPositionInfo (dataSource.retrievePosition())
    }
}