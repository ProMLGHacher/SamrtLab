package com.example.samrtlab.data.repository

import android.content.SharedPreferences
import com.example.samrtlab.domain.model.User
import com.example.samrtlab.domain.repository.UserRepos

class UserReposImpl(
    private val sharedPreferences: SharedPreferences
) : UserRepos {
    override fun getUser(): User {
        return User(
            mail = sharedPreferences.getString(MAIL_TAG, null)
        )
    }

    override fun setUser(user: User) {
        val editor = sharedPreferences.edit()
        editor.putString(MAIL_TAG, user.mail)
        editor.apply()
    }

    override fun updateUser(user: User) {
        val editor = sharedPreferences.edit()
        user.mail?.apply {
            editor.putString(MAIL_TAG, user.mail)
        }
        editor.apply()
    }

    override fun setMail(mail: String) {
        sharedPreferences.edit().putString(MAIL_TAG, mail).apply()
    }

    companion object {
        const val MAIL_TAG = "user_mail"
    }

}