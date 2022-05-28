package com.example.onlinenotesapp.data.models

import java.util.*

data class Note(
    var title: String?,
    var text: String?,
    val creationDate: Date,
    val modificationDate: Date)