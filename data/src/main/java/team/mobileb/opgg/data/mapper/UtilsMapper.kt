package team.mobileb.opgg.data.mapper

import team.mobileb.opgg.data.api.model.PositionInfoResponse
import team.mobileb.opgg.data.api.model.PositionResultResponse
import team.mobileb.opgg.domain.model.PositionInfo
import team.mobileb.opgg.domain.model.PositionResult

object UtilsMapper {

    private fun toPositionResult(positionResultResponse: List<PositionResultResponse>): List<PositionResult> {
        val list = mutableListOf<PositionResult>()
        positionResultResponse.forEach {
            list.add(PositionResult(it.code, it.name))
        }
        return list
    }

    fun toPositionResultResponse(positionResult: List<PositionResult>): List<PositionResultResponse> {
        val list = mutableListOf<PositionResultResponse>()
        positionResult.forEach {
            list.add(PositionResultResponse(it.code, it.name))
        }
        return list
    }

    fun toPositionInfo(positionInfoResponse: PositionInfoResponse): PositionInfo {
        return PositionInfo(
            code = positionInfoResponse.code,
            message = positionInfoResponse.message,
            result = toPositionResult(positionInfoResponse.result),
            responseTime = positionInfoResponse.responseTime
        )
    }

    fun toPositionInfoResponse(positionInfo: PositionInfo): PositionInfoResponse {
        return PositionInfoResponse(
            code = positionInfo.code,
            message = positionInfo.message,
            result = toPositionResultResponse(positionInfo.result),
            responseTime = positionInfo.responseTime
        )
    }
}