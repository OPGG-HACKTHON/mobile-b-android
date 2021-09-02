package team.mobileb.opgg.data.mapper

import team.mobileb.opgg.data.model.PositionInfoResponse
import team.mobileb.opgg.data.model.PositionResultResponse
import team.mobileb.opgg.domain.model.PositionInfo
import team.mobileb.opgg.domain.model.PositionResult

private fun positionResultResponseToDomain(response: PositionResultResponse) = PositionResult(
    code = response.code,
    name = response.name
)

fun PositionInfoResponse.toDomain() = PositionInfo(
    code = code,
    message = message,
    result = result.map(::positionResultResponseToDomain),
    responseTime = responseTime
)
