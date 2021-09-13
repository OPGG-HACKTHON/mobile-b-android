package team.mobileb.opgg.data.datasource

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.callbackFlow
import team.mobileb.opgg.data.api.UtilsApi
import team.mobileb.opgg.data.mapper.toDomain
import team.mobileb.opgg.data.util.isValid
import team.mobileb.opgg.domain.RequestResult
import team.mobileb.opgg.domain.repository.UtilsRepository

class UtilsRepositoryImpl(private val api: UtilsApi) : UtilsRepository {
    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun retrievePosition() = callbackFlow {
        try {
            val request = api.retrievePosition()
            if (request.isValid()) {
                trySend(RequestResult.Success(request.body()!!.toDomain()))
            } else {
                trySend(RequestResult.Fail(Exception(request.errorBody()!!.string())))
            }
        } catch (exception: Exception) {
            trySend(RequestResult.Fail(exception))
        }

        close()
    }
}
