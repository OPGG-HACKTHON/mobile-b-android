package team.mobileb.opgg.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import team.mobileb.opgg.data.api.RoomsApi
import team.mobileb.opgg.data.api.UtilsApi
import team.mobileb.opgg.data.datasource.RoomRepositoryImpl
import team.mobileb.opgg.data.datasource.UtilsRepositoryImpl
import team.mobileb.opgg.domain.repository.RoomRepository
import team.mobileb.opgg.domain.repository.UtilsRepository

@Module
@InstallIn(ViewModelComponent::class)
object RepoModule {
    @Provides
    @ViewModelScoped
    fun provideRoomsRepository(api: RoomsApi): RoomRepository = RoomRepositoryImpl(api)

    @Provides
    @ViewModelScoped
    fun provideUtilsRepository(api: UtilsApi): UtilsRepository = UtilsRepositoryImpl(api)
}
