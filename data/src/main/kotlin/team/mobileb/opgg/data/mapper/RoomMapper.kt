package team.mobileb.opgg.data.mapper

import team.mobileb.opgg.data.model.CheckInfoResponse
import team.mobileb.opgg.data.model.CheckResultResponse
import team.mobileb.opgg.data.model.ResultResponse
import team.mobileb.opgg.data.model.RoomInfoResponse
import team.mobileb.opgg.domain.model.CheckInfo
import team.mobileb.opgg.domain.model.CheckResult
import team.mobileb.opgg.domain.model.Result
import team.mobileb.opgg.domain.model.RoomInfo

private fun ResultResponse.toDomain() = Result(
    roomSeq = roomSeq,
    userKey = userKey,
    inviteCode = inviteCode,
    inviteURL = inviteURL,
    createdAtStr = createdAtStr
)

private fun CheckResultResponse.toDomain() = CheckResult(
    messageMapping = messageMapping,
    sendTo = sendTo
)

fun RoomInfoResponse.toDomain() = RoomInfo(
    code = code,
    message = message,
    result = result.toDomain(),
    responseTime = responseTime
)

fun CheckInfoResponse.toDomain() = CheckInfo(
    code = code,
    message = message,
    result = result.toDomain(),
    responseTime = responseTime
)

