package com.azkz.reservationchecker

import java.time.DayOfWeek
import java.time.LocalTime

object TargetTimes {

    private val dayOfWeekByStartTime = mapOf(
        DayOfWeek.MONDAY to LocalTime.of(20, 0),
        DayOfWeek.TUESDAY to LocalTime.of(20, 0),
        DayOfWeek.THURSDAY to LocalTime.of(20, 0),
        DayOfWeek.WEDNESDAY to LocalTime.of(20, 0),
        DayOfWeek.FRIDAY to LocalTime.of(20, 0),
        DayOfWeek.SATURDAY to LocalTime.of(9, 0),
        DayOfWeek.SUNDAY to LocalTime.of(9, 0),
    )

    /**
     * 開始時間が設定値以降の場合はTrue、それ以外はFalseを返します。
     */
    fun isStartTimeAfterOrEquals(dayOfWeek: DayOfWeek, startTime: LocalTime): Boolean {
        val targetStartTime = dayOfWeekByStartTime.get(dayOfWeek)!!
        return targetStartTime <= startTime
    }

}