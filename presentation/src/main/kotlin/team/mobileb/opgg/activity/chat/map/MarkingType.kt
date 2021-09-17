package team.mobileb.opgg.activity.chat.map

sealed class MarkingType(val value: String) {
    object Warn : MarkingType("Warn")
    object Ward : MarkingType("Ward")
}
