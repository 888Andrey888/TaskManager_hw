package com.example.taskmanager.model

import java.io.Serializable

data class Task(
    val title: String? = null,
    val descriptor: String? = null
) : Serializable
