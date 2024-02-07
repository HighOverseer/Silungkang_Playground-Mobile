package id.rla.silungkangplayground.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.rla.silungkangplayground.data.local.UserPreferenceImpl
import id.rla.silungkangplayground.domain.data.UserPreference

@Module
@InstallIn(SingletonComponent::class)
abstract class UserPreferenceModule {
    @Binds
    abstract fun provideUserPreference(userPreferenceImpl: UserPreferenceImpl):UserPreference
}