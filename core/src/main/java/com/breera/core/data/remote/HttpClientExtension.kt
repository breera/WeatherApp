package com.breera.core.data.remote

import com.breera.core.domain.Result
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.ensureActive
import com.breera.core.domain.DataError
import java.net.UnknownHostException
import kotlin.coroutines.coroutineContext

/**
 * Executes a network call safely, handling exceptions and converting the response
 * into a Result object. This function is designed to catch common network-related
 * exceptions and return a corresponding DataError.
 *
 * @param execute A lambda function that performs the network request and returns an HttpResponse.
 * @return A Result containing either the successful response body or a DataError.
 */
suspend inline fun <reified T> safeCall(
    execute: () -> HttpResponse
): Result<T, DataError.Remote> {
    val response = try {
        execute()
    } catch (e: SocketTimeoutException) {
        // Handle network timeout
        return Result.Error(DataError.Remote.REQUEST_TIMEOUT)
    } catch (e: UnknownHostException) {
        // Handle no internet connection
        return Result.Error(DataError.Remote.NO_INTERNET)
    } catch (e: Exception) {
        // Handle any other exceptions
        e.printStackTrace()
        coroutineContext.ensureActive() // Ensure coroutine is still active
        return Result.Error(DataError.Remote.UNKNOWN)
    }

    return responseToResult(response)
}

/**
 * Converts an HttpResponse into a Result object, handling various HTTP status codes.
 * This function attempts to deserialize the response body into the expected type T.
 *
 * @param response The HttpResponse received from the network call.
 * @return A Result containing either the successful response body or a DataError based on the status code.
 */
suspend inline fun <reified T> responseToResult(
    response: HttpResponse
): Result<T, DataError.Remote> {
    return when (response.status.value) {
        in 200..299 -> {
            // Handle successful response
            return try {
                Result.Success(response.body<T>())
            } catch (e: NoTransformationFoundException) {
                // Handle serialization error
                Result.Error(DataError.Remote.SERIALIZATION)
            }
        }

        408 -> Result.Error(DataError.Remote.REQUEST_TIMEOUT) // Handle request timeout
        429 -> Result.Error(DataError.Remote.TOO_MANY_REQUESTS) // Handle too many requests
        in 500..599 -> Result.Error(DataError.Remote.SERVER) // Handle server errors
        400 -> Result.Error(DataError.Remote.NO_MATCH_FOUND) // Handle client error with no match found

        else -> Result.Error(DataError.Remote.UNKNOWN) // Handle unknown errors
    }
}