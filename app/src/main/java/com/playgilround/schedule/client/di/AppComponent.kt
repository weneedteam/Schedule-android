package com.playgilround.schedule.client.di

import com.playgilround.schedule.client.signin.SignInPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class,
            UserModule::class])
interface AppComponent {
    fun inject(presenter: SignInPresenter)
}

