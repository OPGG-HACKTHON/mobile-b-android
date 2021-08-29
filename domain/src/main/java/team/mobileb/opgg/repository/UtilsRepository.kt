package team.mobileb.opgg.repository

import team.mobileb.opgg.model.PositionInfo

interface UtilsRepository {
    suspend fun retirevePosition() : PositionInfo
}