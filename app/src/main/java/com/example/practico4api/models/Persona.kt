package com.example.practico4api.models

data class Persona (
    val id: Long,
    val name: String,
    val last_name: String,
    val company: String,
    val address: String,
    val city: String,
    val state: String,
    val profile_picture: String,
    val phones: List<Phone>,
    val emails: List<Email>
)

data class Phone (
    val id: Long,
    val label: String,
    val number: String
)

data class Email (
    val id: Long,
    val email: String,
    val label: String
)
