package com.stefware.myapplication.data.model

data class Meeting(
    val id: Int = 0,
    val title: String = "",
    val date: String = "",
    val time: String = "",
    val link: String = "",
    val host: Int = 0,
    val members: List<Int> = emptyList(),
    val accessCode: String = "",
    val recording: Recording? = null
)

data class Recording(
    val recordingLink: String = "",
    val duration: String = "",
    val publicAccess: Boolean = false
)