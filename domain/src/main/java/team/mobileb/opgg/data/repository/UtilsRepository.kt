package team.mobileb.opgg.data.repository

import team.mobileb.opgg.domain.model.PositionInfo

interface UtilsRepository {
    suspend fun retirevePosition(): PositionInfo
}