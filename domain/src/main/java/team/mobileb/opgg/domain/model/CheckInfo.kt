package team.mobileb.opgg.domain.model

data class CheckInfo (
     val code: Int,
     val message: String,
     val result: CheckResult,
     val responseTime: String

)