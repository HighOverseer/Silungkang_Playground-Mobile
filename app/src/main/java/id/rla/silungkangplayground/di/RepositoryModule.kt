package id.rla.silungkangplayground.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.rla.silungkangplayground.data.RepositoryImpl
import id.rla.silungkangplayground.domain.data.Repository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideRepository(repositoryImpl: RepositoryImpl):Repository

}