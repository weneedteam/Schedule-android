package com.playgilround.schedule.client.di

import com.playgilround.schedule.client.signin.SignInPresenter
import com.playgilround.schedule.client.tutorial.TutorialPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class,
            UserModule::class])
interface AppComponent {
    fun signInInject(presenter: SignInPresenter)
    fun tutorialInject(presenter: TutorialPresenter)
}

