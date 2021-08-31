package team.mobileb.opgg.data.mapper

import team.mobileb.opgg.data.model.PositionInfoResponse
import team.mobileb.opgg.data.model.PositionResultResponse
import team.mobileb.opgg.domain.model.PositionInfo
import team.mobileb.opgg.domain.model.PositionResult

private fun PositionResultResponse.toDomain() = PositionResult(
    code = code,
    name = name
)

fun PositionInfoResponse.toDomain() = PositionInfo(
    code = code,
    message = message,
    result = result.map { it.toDomain() },
    responseTime = responseTime
)
