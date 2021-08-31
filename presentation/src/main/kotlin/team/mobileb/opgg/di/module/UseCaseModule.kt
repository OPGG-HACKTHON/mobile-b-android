package team.mobileb.opgg.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import team.mobileb.opgg.domain.repository.RoomRepository
import team.mobileb.opgg.domain.repository.UtilsRepository
import team.mobileb.opgg.domain.usecase.CheckRoomUseCase
import team.mobileb.opgg.domain.usecase.CreateRoomUseCase
import team.mobileb.opgg.domain.usecase.RetrievePositionUseCase
import team.mobileb.opgg.domain.usecase.RetrieveRoomUseCase

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {
    @Provides
    @ViewModelScoped
    fun provideCheckRoomUseCase(repository: RoomRepository) = CheckRoomUseCase(repository)

    @Provides
    @ViewModelScoped
    fun provideCreateRoomUseCase(repository: RoomRepository) = CreateRoomUseCase(repository)

    @Provides
    @ViewModelScoped
    fun provideRetrieveRoomUseCase(repository: RoomRepository) = RetrieveRoomUseCase(repository)

    @Provides
    @ViewModelScoped
    fun provideRetrievePositionUseCase(repository: UtilsRepository) =
        RetrievePositionUseCase(repository)
}
