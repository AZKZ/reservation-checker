package com.azkz.reservationchecker

interface INotificator {

    /**
     * 通知を送信する。
     */
    suspend fun send(title: String, message: String)
}