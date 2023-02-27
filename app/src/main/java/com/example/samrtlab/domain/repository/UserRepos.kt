package com.example.samrtlab.domain.repository

import com.example.samrtlab.domain.model.User

interface UserRepos {
    fun getUser() : User
    fun setUser(user: User)
    fun setMail(mail: String)
}