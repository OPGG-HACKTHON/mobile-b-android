package team.mobileb.opgg.activity.room

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import team.mobileb.opgg.domain.model.CreateRoomData
import team.mobileb.opgg.domain.usecase.CheckRoomUseCase
import team.mobileb.opgg.domain.usecase.CreateRoomUseCase
import team.mobileb.opgg.domain.usecase.RetrievePositionUseCase
import team.mobileb.opgg.domain.usecase.RetrieveRoomUseCase
import javax.inject.Inject

@HiltViewModel
class RoomViewModel @Inject constructor(
    private val checkRoomUseCase: CheckRoomUseCase,
    private val createRoomUseCase: CreateRoomUseCase,
    private val retrieveRoomUseCase: RetrieveRoomUseCase,
    private val retrievePositionUseCase: RetrievePositionUseCase,
) : ViewModel() {
    suspend fun checkRoom(inviteCode: String) = checkRoomUseCase(inviteCode)

    suspend fun createRoom(createRoomData: CreateRoomData) = createRoomUseCase(createRoomData)

    suspend fun retrieveRoom(userKey: String) = retrieveRoomUseCase(userKey)

    suspend fun retrievePosition() = retrievePositionUseCase()
}
