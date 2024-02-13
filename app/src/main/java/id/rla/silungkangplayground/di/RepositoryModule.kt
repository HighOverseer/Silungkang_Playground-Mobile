package id.rla.silungkangplayground.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.rla.silungkangplayground.data.RepositoryImpl
import id.rla.silungkangplayground.data.helper.QrCodeGenerator
import id.rla.silungkangplayground.data.helper.QrCodeGeneratorImpl
import id.rla.silungkangplayground.data.local.UserPreferenceImpl
import id.rla.silungkangplayground.domain.data.Repository
import id.rla.silungkangplayground.domain.data.UserPreference

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideRepository(repositoryImpl: RepositoryImpl):Repository

    @Binds
    abstract fun provideUserPreference(userPreferenceImpl: UserPreferenceImpl): UserPreference

    @Binds
    abstract fun provideQrCodeGenerator(qrCodeGeneratorImpl: QrCodeGeneratorImpl):QrCodeGenerator

}