package com.azkz.reservationchecker

import org.jsoup.Connection
import org.jsoup.Jsoup
import java.time.LocalDate
import java.time.LocalTime

class MemberPageParser : IMemberPageParser {

    val cookies: Map<String, String>

    companion object {
        const val LOGIN_URL = "https://www.spoon3.jp/reserve/index.php"
        const val LESSON_SELECT_URL = "https://www.spoon3.jp/reserve/index.php?_action=room_select&site=pc"
    }

    init {

        // ログイン時に送信するFormデータ
        val loginFormData = mapOf(
            "form[user]" to AppicationProperties.USER_ID,
            "form[cookie]" to "on",
            "form[mobile_password]" to AppicationProperties.USER_PASSWORD,
            "submit" to "ログイン",
            "_action" to "login",
            "site" to "pc",
            "s" to "330",
            "mode" to "check",
        )

        // ログイン情報を送信する。
        val response = Jsoup.connect(LOGIN_URL)
            .method(Connection.Method.POST)
            .data(loginFormData)
            .header("Content-Type", "application/x-www-form-urlencoded")
            .execute()

        // クッキーを保存する。
        cookies = response.cookies()
    }


    /**
     * Lesson情報を抽出する。
     */
    override fun extractLessons(): LessonLot {

        val document = Jsoup.connect(LESSON_SELECT_URL)
            .cookies(cookies)
            .get()


        val tBody = document.select("#calender_table > tbody")[1]
        val dateList = mutableListOf<LocalDate>()
        val lessons = LessonLot()

        for (tr in tBody.getElementsByTag("tr")) {

            // 日付行の場合
            if (tr.wholeText().contains("予約枠")) {
                for (element in tr.getElementsByTag("th")) {
                    // (月)などの曜日部分を除く。
                    val dateText = element.ownText().dropLast(3)

                    // 月、日を切り出す。
                    val month = dateText.split("/")[0].toInt()
                    val day = dateText.split("/")[1].toInt()

                    // 日付リストに追加する。
                    dateList += LocalDate.of(LocalDate.now().year, month, day)
                }
            }

            // 各時間帯の行の場合
            if (tr.wholeText().contains("～")) {

                var startTime: LocalTime? = null
                var endTime: LocalTime? = null
                for ((colNum, element) in tr.getElementsByTag("td").withIndex()) {
                    // 先頭の時間帯列の場合は時間帯を取得する。
                    if (colNum == 0) {
                        // 時間
                        val hourText = element.ownText().split("～")

                        // 開始時間/終了時間
                        val startTimeText = hourText[0].split(":")
                        val endTimeText = hourText[1].split(":")
                        startTime = LocalTime.of(startTimeText[0].toInt(), startTimeText[1].toInt())
                        endTime = LocalTime.of(endTimeText[0].toInt(), endTimeText[1].toInt())
                    } else {
                        // 予約セル
                        val reservationCell = element.child(0)
                        var cellText = reservationCell.ownText()

                        // 予約可能数
                        val numOfReservable = cellText.takeLast(2).dropLast(1).toInt()
                        // 自分の予約かどうか
                        val isMyReserved = reservationCell.hasClass("current_reserve")

                        // レッスン
                        val lesson = Lesson(
                            date = dateList[colNum - 1],
                            startTime = startTime!!,
                            endTime = endTime!!,
                            isMyReserved = isMyReserved,
                            numOfReservable = numOfReservable
                        )

                        // レッスンリストに追加する。
                        lessons.add(lesson)
                    }
                }

            }

        }

        return lessons
    }
}