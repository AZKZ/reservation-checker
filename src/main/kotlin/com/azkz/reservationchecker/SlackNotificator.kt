package com.azkz.reservationchecker

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class SlackNotificator : INotificator {

    private val client = HttpClient(CIO) {
        install(JsonFeature)
    }

    /**
     * 通知を送信する。
     */
    override suspend fun send(title: String, message: String) {

        // Markdownでタイトルを装飾してメッセージと結合する。
        val messageWithTitle = "<!channel> *【${title}】*" + "\n" + message

        // Incoming Webhookにメッセージを送信する。
        client.post<HttpResponse>(AppicationProperties.WEB_HOOK_URL) {
            contentType(ContentType.Application.Json)
            body = Message(messageWithTitle)
        }
    }


    /**
     * Incoming Webhookで使用するDataクラス
     * Json化を楽するためにDataクラスにしている。
     */
    private data class Message(val text: String)
}