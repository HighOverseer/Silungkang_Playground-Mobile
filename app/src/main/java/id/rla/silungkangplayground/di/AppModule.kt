package id.rla.silungkangplayground.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import id.rla.silungkangplayground.domain.usecase.interactor.ChangeCurrentMemberAccountInteractor
import id.rla.silungkangplayground.domain.usecase.ChangeCurrentMemberAccountUseCase
import id.rla.silungkangplayground.domain.usecase.interactor.CheckInMemberInteractor
import id.rla.silungkangplayground.domain.usecase.CheckInMemberUseCase
import id.rla.silungkangplayground.domain.usecase.CheckIsUserHasAlreadyLoggedInUseCase
import id.rla.silungkangplayground.domain.usecase.ExchangePointUseCase
import id.rla.silungkangplayground.domain.usecase.interactor.GetCardMemberInteractor
import id.rla.silungkangplayground.domain.usecase.GetCardMemberUseCase
import id.rla.silungkangplayground.domain.usecase.interactor.GetCurrentMemberIdInteractor
import id.rla.silungkangplayground.domain.usecase.GetCurrentMemberIdUseCase
import id.rla.silungkangplayground.domain.usecase.interactor.GetDetailMemberVoucherInteractor
import id.rla.silungkangplayground.domain.usecase.GetDetailMemberVoucherUseCase
import id.rla.silungkangplayground.domain.usecase.GetEventPlaygroundInPagingUseCase
import id.rla.silungkangplayground.domain.usecase.interactor.GetMemberHistoryInteractor
import id.rla.silungkangplayground.domain.usecase.GetMemberHistoryUseCase
import id.rla.silungkangplayground.domain.usecase.GetOfferedVouchersUseCase
import id.rla.silungkangplayground.domain.usecase.interactor.LoginInteractor
import id.rla.silungkangplayground.domain.usecase.LoginUseCase
import id.rla.silungkangplayground.domain.usecase.interactor.LogoutInteractor
import id.rla.silungkangplayground.domain.usecase.LogoutUseCase
import id.rla.silungkangplayground.domain.usecase.SendFeedbackUseCase
import id.rla.silungkangplayground.domain.usecase.interactor.CheckIsUserHasAlreadyLoggedInInteractor
import id.rla.silungkangplayground.domain.usecase.interactor.ExchangePointInteractor
import id.rla.silungkangplayground.domain.usecase.interactor.GetEventPlaygroundInPagingInteractor
import id.rla.silungkangplayground.domain.usecase.interactor.GetOfferedVoucherInteractor
import id.rla.silungkangplayground.domain.usecase.interactor.SendFeedbackInteractor

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
    abstract fun provideGetMemberHistoryUseCase(getMemberHistoryInteractor: GetMemberHistoryInteractor):GetMemberHistoryUseCase

    @Binds
    @ViewModelScoped
    abstract fun provideGetCardMemberUseCase(getCardMemberInteractor: GetCardMemberInteractor):GetCardMemberUseCase

    @Binds
    @ViewModelScoped
    abstract fun provideCheckInMemberInteractor(checkInMemberInteractor: CheckInMemberInteractor):CheckInMemberUseCase

    @Binds
    @ViewModelScoped
    abstract fun provideGetCurrentMemberIdUseCase(getCurrentMemberIdInteractor: GetCurrentMemberIdInteractor):GetCurrentMemberIdUseCase

    @Binds
    @ViewModelScoped
    abstract fun provideChangeCurrentMemberAccountUseCase(changeCurrentMemberAccountInteractor: ChangeCurrentMemberAccountInteractor):ChangeCurrentMemberAccountUseCase

    @Binds
    @ViewModelScoped
    abstract fun provideLogoutUseCase(logoutInteractor: LogoutInteractor):LogoutUseCase

    @Binds
    @ViewModelScoped
    abstract fun provideGetOfferedVoucherUseCase(getOfferedVoucherInteractor: GetOfferedVoucherInteractor):GetOfferedVouchersUseCase

    @Binds
    @ViewModelScoped
    abstract fun provideExchangePointUseCase(exchangePointInteractor: ExchangePointInteractor):ExchangePointUseCase

    @Binds
    @ViewModelScoped
    abstract fun provideCheckIsUserHasAlreadyLoggedInUseCase(checkIsUserHasAlreadyLoggedInInteractor: CheckIsUserHasAlreadyLoggedInInteractor):CheckIsUserHasAlreadyLoggedInUseCase

    @Binds
    @ViewModelScoped
    abstract fun provideSendFeedbackUseCase(sendFeedbackInteractor: SendFeedbackInteractor):SendFeedbackUseCase

    @Binds
    @ViewModelScoped
    abstract fun provideGetEventPlaygroundInPagingUseCase(getEventPlaygroundInPagingInteractor: GetEventPlaygroundInPagingInteractor):GetEventPlaygroundInPagingUseCase
}