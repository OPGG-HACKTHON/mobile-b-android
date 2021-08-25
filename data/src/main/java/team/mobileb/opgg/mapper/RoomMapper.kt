package team.mobileb.opgg.mapper

import team.mobileb.opgg.api.model.CheckInfoResponse
import team.mobileb.opgg.api.model.CheckResultResponse
import team.mobileb.opgg.api.model.ResultResponse
import team.mobileb.opgg.api.model.RoomInfoResponse
import team.mobileb.opgg.model.CheckInfo
import team.mobileb.opgg.model.CheckResult
import team.mobileb.opgg.model.Result
import team.mobileb.opgg.model.RoomInfo

object RoomMapper {

    private fun mapToResult(resultResponse: ResultResponse): Result {
        return Result(
            roomSeq = resultResponse.roomSeq,
            userKey = resultResponse.userKey,
            inviteCode = resultResponse.inviteCode,
            inviteURL = resultResponse.inviteURL,
            createdAtStr = resultResponse.createdAtStr
        )
    }

    private fun mapToResultResponse(result: Result): ResultResponse {
        return ResultResponse(
            roomSeq = result.roomSeq,
            userKey = result.userKey,
            inviteCode = result.inviteCode,
            inviteURL = result.inviteURL,
            createdAtStr = result.createdAtStr
        )
    }

    private fun mapToCheckResult(checkResultResponse: CheckResultResponse): CheckResult {
        return CheckResult(
            messageMapping = checkResultResponse.messageMapping,
            sendTo = checkResultResponse.sendTo
        )

    }

    private fun mapToCheckResultResponse(checkResult: CheckResult): CheckResultResponse {
        return CheckResultResponse(
            messageMapping = checkResult.messageMapping,
            sendTo = checkResult.sendTo
        )
    }


    fun mapperToRoomInfo(roomInfoResponse: RoomInfoResponse): RoomInfo {
        return RoomInfo(
            code = roomInfoResponse.code,
            message = roomInfoResponse.message,
            result = mapToResult(roomInfoResponse.result),
            responseTime = roomInfoResponse.responseTime
        )
    }

    fun mapperToRoomInfoResponse(roomInfo: RoomInfo): RoomInfoResponse {
        return RoomInfoResponse(
            code = roomInfo.code,
            message = roomInfo.message,
            result = mapToResultResponse(roomInfo.result),
            responseTime = roomInfo.responseTime
        )
    }

    fun mapperToCheckInfo(checkInfoResponse: CheckInfoResponse): CheckInfo {
        return CheckInfo(
            code = checkInfoResponse.code,
            message = checkInfoResponse.message,
            result = mapToCheckResult(checkInfoResponse.result),
            responseTime = checkInfoResponse.responseTime
        )

    }

    fun mapperToCheckInfoResponse(checkInfo: CheckInfo): CheckInfoResponse {
        return CheckInfoResponse(
            code = checkInfo.code,
            message = checkInfo.message,
            result = mapToCheckResultResponse(checkInfo.result),
            responseTime = checkInfo.responseTime
        )
    }

}