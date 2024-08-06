package ir.sinanakhaei.retrofit

import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.message
import com.skydoves.sandwich.operators.ApiResponseSuspendOperator
import ir.sinanakhaei.retrofit.model.Tags
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class ResponseOperator<T> : ApiResponseSuspendOperator<T>() {

    override suspend fun onError(apiResponse: ApiResponse.Failure.Error) =
        withContext(Dispatchers.Main) {
            apiResponse.run {
                Timber
                    .tag(Tags.NETWORK_RESULT)
                    .e("error: ${message()}")
            }
        }

    override suspend fun onException(apiResponse: ApiResponse.Failure.Exception) =
        withContext(Dispatchers.Main) {
            apiResponse.run {
                Timber
                    .tag(Tags.NETWORK_RESULT)
                    .e("exception: $apiResponse")
            }
        }

    override suspend fun onSuccess(apiResponse: ApiResponse.Success<T>) {
        Timber
            .tag(Tags.NETWORK_RESULT)
            .d("success: ${apiResponse.data}")
    }
}