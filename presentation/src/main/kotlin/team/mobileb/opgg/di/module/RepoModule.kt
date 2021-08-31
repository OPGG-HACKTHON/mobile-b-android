package team.mobileb.opgg.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.mobileb.opgg.data.api.RoomsApi
import team.mobileb.opgg.data.api.UtilsApi
import team.mobileb.opgg.data.datasource.RoomRepositoryImpl
import team.mobileb.opgg.data.datasource.UtilsRepositoryImpl
import team.mobileb.opgg.domain.repository.RoomRepository
import team.mobileb.opgg.domain.repository.UtilsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {
    @Provides
    @Singleton
    fun provideRoomsRepository(api: RoomsApi): RoomRepository = RoomRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideUtilsRepository(api: UtilsApi): UtilsRepository = UtilsRepositoryImpl(api)
}
