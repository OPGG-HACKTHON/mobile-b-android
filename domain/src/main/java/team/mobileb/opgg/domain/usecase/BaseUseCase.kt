package team.mobileb.opgg.domain.usecase

import kotlinx.coroutines.flow.Flow
import team.mobileb.opgg.domain.RequestResult

interface BaseUseCase<in Parameter, out ResultType> {
    suspend operator fun invoke(parameter: Parameter): Flow<RequestResult<ResultType>>
}
