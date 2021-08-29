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

    private fun toResult(resultResponse: ResultResponse): Result {
        return Result(
            roomSeq = resultResponse.roomSeq,
            userKey = resultResponse.userKey,
            inviteCode = resultResponse.inviteCode,
            inviteURL = resultResponse.inviteURL,
            createdAtStr = resultResponse.createdAtStr
        )
    }

    private fun toResultResponse(result: Result): ResultResponse {
        return ResultResponse(
            roomSeq = result.roomSeq,
            userKey = result.userKey,
            inviteCode = result.inviteCode,
            inviteURL = result.inviteURL,
            createdAtStr = result.createdAtStr
        )
    }

    private fun toCheckResult(checkResultResponse: CheckResultResponse): CheckResult {
        return CheckResult(
            messageMapping = checkResultResponse.messageMapping,
            sendTo = checkResultResponse.sendTo
        )

    }

    private fun toCheckResultResponse(checkResult: CheckResult): CheckResultResponse {
        return CheckResultResponse(
            messageMapping = checkResult.messageMapping,
            sendTo = checkResult.sendTo
        )
    }


    fun toRoomInfo(roomInfoResponse: RoomInfoResponse): RoomInfo {
        return RoomInfo(
            code = roomInfoResponse.code,
            message = roomInfoResponse.message,
            result = toResult(roomInfoResponse.result),
            responseTime = roomInfoResponse.responseTime
        )
    }

    fun toRoomInfoResponse(roomInfo: RoomInfo): RoomInfoResponse {
        return RoomInfoResponse(
            code = roomInfo.code,
            message = roomInfo.message,
            result = toResultResponse(roomInfo.result),
            responseTime = roomInfo.responseTime
        )
    }

    fun toCheckInfo(checkInfoResponse: CheckInfoResponse): CheckInfo {
        return CheckInfo(
            code = checkInfoResponse.code,
            message = checkInfoResponse.message,
            result = toCheckResult(checkInfoResponse.result),
            responseTime = checkInfoResponse.responseTime
        )

    }

    fun toCheckInfoResponse(checkInfo: CheckInfo): CheckInfoResponse {
        return CheckInfoResponse(
            code = checkInfo.code,
            message = checkInfo.message,
            result = toCheckResultResponse(checkInfo.result),
            responseTime = checkInfo.responseTime
        )
    }

}