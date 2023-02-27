package com.example.samrtlab.domain.repository

interface PasswordRepository {
    fun getPassword() : String?
    fun setPassword(password: String)
}