package com.playgilround.schedule.client.signup.model

interface UserDataModel {

    fun getNameField(): String

    fun getEmailField(): String

    fun getPasswordField(): String

    fun getRepeatPasswordField(): String

    fun getNicknameField(): String

    fun getBirthField(): String
}