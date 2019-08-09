package com.playgilround.schedule.client.signup.view

interface OnSignUpAdapterListener {

    fun onNextField(position: Int)

    fun disableNextButton()

    fun ableNextButton()
}