package com.azkz.reservationchecker

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.TextStyle
import java.util.*

class Lesson(
    /** 日付 */
    val date: LocalDate,
    /** 開始時刻 */
    val startTime: LocalTime,
    /** 終了時刻 */
    val endTime: LocalTime,
    /** 自分の予約かどうか */
    val isMyReserved: Boolean,
    /** 予約可能な数 */
    val numOfReservable: Int
) : Comparable<Lesson> {

    /**
     * 新たに予約が可能かどうかを判定する。
     * @return 自分が予約していない かつ 予約可能な数がある場合はtrue,それ以外はfalse
     */
    fun canNewReserve(): Boolean {
        return !isMyReserved && numOfReservable > 0
    }

    /**
     * 通知対象かどうかを判定する。
     * @return 通知対象の場合はtrue,それ以外はfalse
     */
    fun isSubjectToNotification(): Boolean {
        val LIMIT_DAYS: Long = 3

        val upperDate = LocalDate.now().plusDays(LIMIT_DAYS)

        return this.canNewReserve() and
                TargetTimes.isStartTimeAfterOrEquals(this.date.dayOfWeek, this.startTime) and
                (this.date <= upperDate)
    }

    /**
     * 通知のための文字列
     */
    fun toNotificationString(): String {
        return "$date (${date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.JAPANESE)}) $startTime - $endTime"
    }

    /**
     * Compares this object with the specified object for order. Returns zero if this object is equal
     * to the specified [other] object, a negative number if it's less than [other], or a positive number
     * if it's greater than [other].
     */
    override fun compareTo(other: Lesson): Int {
        if (this.date.isEqual(other.date)) {
            return this.startTime.compareTo(this.startTime)
        } else {
            return this.date.compareTo(other.date)
        }
    }
}