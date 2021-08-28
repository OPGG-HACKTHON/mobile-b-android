package team.mobileb.opgg.model

data class PositionInfo(
    val code : Int,
    val message : String,
    val result : List<PositionResult>,
    val responseTime : String
)
