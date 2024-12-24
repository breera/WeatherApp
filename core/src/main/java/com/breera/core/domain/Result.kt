package com.breera.core.domain

/**
 * A sealed interface representing a result that can be either a success or an error.
 * It is parameterized with a success type [D] and an error type [E].
 */
sealed interface Result<out D, out E: Error> {

    /**
     * Represents a successful result containing data of type [D].
     * @param data The data associated with the success.
     */
    data class Success<out D>(val data: D): Result<D, Nothing>

    /**
     * Represents an error result containing an error of type [E].
     * @param error The error associated with the failure.
     */
    data class Error<out E: com.breera.core.domain.Error>(val error: E): Result<Nothing, E>
}

/**
 * Transforms the success data of a [Result] using the provided [map] function.
 * If the result is an error, it is returned unchanged.
 *
 * @param map A function to transform the success data.
 * @return A new [Result] with transformed success data or the original error.
 */
inline fun <T, E: Error, R> Result<T, E>.map(map: (T) -> R): Result<R, E> {
    return when(this) {
        is Result.Error -> Result.Error(error)
        is Result.Success -> Result.Success(map(data))
    }
}

/**
 * Converts a [Result] to an [EmptyResult], which is a [Result] with a [Unit] success type.
 * This is useful for operations where the success data is not needed.
 *
 * @return An [EmptyResult] with the same error type.
 */
fun <T, E: Error> Result<T, E>.asEmptyDataResult(): EmptyResult<E> {
    return map {  }
}

/**
 * Executes the given [action] if the result is a success.
 * The result is returned unchanged.
 *
 * @param action A function to execute on the success data.
 * @return The original [Result].
 */
inline fun <T, E: Error> Result<T, E>.onSuccess(action: (T) -> Unit): Result<T, E> {
    return when(this) {
        is Result.Error -> this
        is Result.Success -> {
            action(data)
            this
        }
    }
}

/**
 * Executes the given [action] if the result is an error.
 * The result is returned unchanged.
 *
 * @param action A function to execute on the error data.
 * @return The original [Result].
 */
inline fun <T, E: Error> Result<T, E>.onError(action: (E) -> Unit): Result<T, E> {
    return when(this) {
        is Result.Error -> {
            action(error)
            this
        }
        is Result.Success -> this
    }
}

/**
 * A type alias for a [Result] with a [Unit] success type, indicating no data is returned on success.
 */
typealias EmptyResult<E> = Result<Unit, E>