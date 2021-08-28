package team.mobileb.opgg.model

data class Result(
    val roomSeq: Int,
    val userKey: String,
    val inviteCode: String,
    val inviteURL: String,
    val createdAtStr: String
)
