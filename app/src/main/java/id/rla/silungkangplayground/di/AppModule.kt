package id.rla.silungkangplayground.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import id.rla.silungkangplayground.domain.usecase.GetCardMemberInteractor
import id.rla.silungkangplayground.domain.usecase.GetCardMemberUseCase
import id.rla.silungkangplayground.domain.usecase.GetDetailMemberVoucherInteractor
import id.rla.silungkangplayground.domain.usecase.GetDetailMemberVoucherUseCase
import id.rla.silungkangplayground.domain.usecase.GetMemberHistoryInteractor
import id.rla.silungkangplayground.domain.usecase.GetMemberHistoryUseCase
import id.rla.silungkangplayground.domain.usecase.LoginInteractor
import id.rla.silungkangplayground.domain.usecase.LoginUseCase

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppModule {

    @Binds
    @ViewModelScoped
    abstract fun provideLoginUseCase(loginInteractor: LoginInteractor):LoginUseCase

    @Binds
    @ViewModelScoped
    abstract fun provideGetDetailMemberVoucherUseCase(getDetailMemberVoucherInteractor: GetDetailMemberVoucherInteractor):GetDetailMemberVoucherUseCase

    @Binds
    @ViewModelScoped
    abstract fun provideGetMemberHistoryInteractor(getMemberHistoryInteractor: GetMemberHistoryInteractor):GetMemberHistoryUseCase

    @Binds
    @ViewModelScoped
    abstract fun provideGetCardMemberInteractor(getCardMemberInteractor: GetCardMemberInteractor):GetCardMemberUseCase

}