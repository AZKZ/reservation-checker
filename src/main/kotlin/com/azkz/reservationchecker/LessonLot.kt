package com.azkz.reservationchecker

import java.time.LocalDate

class LessonLot(private val list: MutableList<Lesson> = mutableListOf<Lesson>()) : Iterable<Lesson> {

    override fun iterator(): Iterator<Lesson> {
        return list.iterator()
    }

    fun add(element: Lesson) {
        this.list += element
    }

    fun size(): Int {
        return this.list.size
    }

    /**
     * 設定値による絞り込みを行う。
     */
    fun filterBySetting(): LessonLot {
        val LIMIT_DAYS: Long = 3

        val upperDate = LocalDate.now().plusDays(LIMIT_DAYS)

        val filteredList = this.list.filter { lesson -> lesson.isSubjectToNotification() }.sorted().toMutableList()

        return LessonLot(list = filteredList)
    }

    /**
     * 通知のための文字列
     */
    fun toNotificationString(): String {
        var notificationText = ""
        for (reservable in this.list) {
            notificationText += reservable.toNotificationString() + "\n"
        }
        return notificationText
    }
}