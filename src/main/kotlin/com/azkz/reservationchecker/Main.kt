package com.azkz.reservationchecker

suspend fun main() {
    val parser = MemberPageParser()
    val lessons = parser.extractLessons()

    val reservableLessons = lessons.filterBySetting()

    if (reservableLessons.size() > 0) {
        val notificator: INotificator = SlackNotificator()
        notificator.send(title = "Spoon空き時間通知", message = reservableLessons.toNotificationString())
    }
}