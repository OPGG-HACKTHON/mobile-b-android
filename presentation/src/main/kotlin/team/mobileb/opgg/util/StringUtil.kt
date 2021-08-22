package team.mobileb.opgg.util

object StringUtil {
    fun getRandom(length: Int) = List(length) { ('가'..'힣').random() }.joinToString("")
}
