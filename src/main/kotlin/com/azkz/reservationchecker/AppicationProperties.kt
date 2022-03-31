package com.azkz.reservationchecker

import java.util.*

object AppicationProperties {
    private val properties = ResourceBundle.getBundle("application");

    val WEB_HOOK_URL= properties.getString("webhook.url")
    val USER_ID= properties.getString("user.id")
    val USER_PASSWORD= properties.getString("user.password")
}