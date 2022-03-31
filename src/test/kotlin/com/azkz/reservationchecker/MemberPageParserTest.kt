package com.azkz.reservationchecker

import org.junit.jupiter.api.Test

internal class MemberPageParserTest {

    @Test
    fun generateTest() {
        val parser = MemberPageParser()
        val lessonList = parser.extractLessons()
        print("")
    }

}