package team.mobileb.opgg.data.datasource

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import team.mobileb.opgg.data.api.RoomsApi
import team.mobileb.opgg.data.mapper.toDomain
import team.mobileb.opgg.data.util.isValid
import team.mobileb.opgg.domain.RequestResult
import team.mobileb.opgg.domain.repository.RoomRepository

class RoomRepositoryImpl(private val api: RoomsApi) : RoomRepository {
    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun createRoom(userKey: String) = callbackFlow {
        try {
            val request = api.createRoom(userKey)
            if (request.isValid()) {
                trySend(RequestResult.Success(request.body()!!.toDomain()))
            } else {
                trySend(RequestResult.Fail(Exception(request.errorBody()!!.string())))
            }
        } catch (exception: Exception) {
            trySend(RequestResult.Fail(exception))
        }

        awaitClose { close() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun retrieveRoom(userKey: String) = callbackFlow {
        try {
            val request = api.retrieveRoom(userKey)
            if (request.isValid()) {
                trySend(RequestResult.Success(request.body()!!.toDomain()))
            } else {
                trySend(RequestResult.Fail(Exception(request.errorBody()!!.string())))
            }
        } catch (exception: Exception) {
            trySend(RequestResult.Fail(exception))
        }

        awaitClose { close() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun checkRoom(inviteCode: String) = callbackFlow {
        try {
            val request = api.checkRoom(inviteCode)
            if (request.isValid()) {
                trySend(RequestResult.Success(request.body()!!.toDomain()))
            } else {
                trySend(RequestResult.Fail(Exception(request.errorBody()!!.string())))
            }
        } catch (exception: Exception) {
            trySend(RequestResult.Fail(exception))
        }

        awaitClose { close() }
    }
}
