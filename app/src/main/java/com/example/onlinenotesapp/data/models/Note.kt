package com.example.onlinenotesapp.data.models

import java.io.Serializable

data class Note(
    val id: String = "",
    var title: String = "",
    var text: String = "",
    val creationDate: String = "",
    val modificationDate: String = ""): Serializable