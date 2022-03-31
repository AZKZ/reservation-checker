package com.azkz.reservationchecker

interface IMemberPageParser {

    /**
     * Lesson情報を抽出する。
     */
    fun extractLessons(): LessonLot
}