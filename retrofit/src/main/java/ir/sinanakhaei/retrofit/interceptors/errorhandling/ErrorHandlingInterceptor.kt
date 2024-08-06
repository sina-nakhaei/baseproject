package ir.sinanakhaei.retrofit.interceptors.errorhandling

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ErrorHandlingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        // Handle different HTTP status codes
        when (response.code) {
            in 400..499 -> handleClientErrors(response)
            in 500..599 -> handleServerErrors(response)
        }

        return response
    }

    private fun handleClientErrors(response: Response) {
        when (response.code) {
            400 -> throw BadRequestException() // Bad Request
            401 -> throw UnauthorizedException() // Unauthorized
            403 -> throw ForbiddenException()    // Forbidden
            404 -> throw NotFoundException()     // Not Found
            408 -> throw RequestTimeoutException() // Request Timeout
            429 -> throw RateLimitExceededException()  // Too Many Requests
            else -> throw ClientErrorException(response.code)
        }
    }

    private fun handleServerErrors(response: Response) {
        when (response.code) {
            500 -> throw InternalServerErrorException() // Internal Server Error
            502 -> throw BadGatewayException()           // Bad Gateway
            504 -> throw GatewayTimeoutException()       // Gateway Timeout
            503 -> throw ServiceUnavailableException() // Service Unavailable
            else -> throw ServerErrorException(response.code)
        }
    }
}

class BadRequestException : IOException() {
    override val message: String
        get() = "Bad Request (400)"
}

class UnauthorizedException : IOException() {
    override val message: String
        get() = "Unauthorized (401)"
}

class ForbiddenException : IOException() {
    override val message: String
        get() = "Forbidden (403)"
}

class NotFoundException : IOException() {
    override val message: String
        get() = "Not Found (404)"
}

class RequestTimeoutException : IOException() {
    override val message: String
        get() = "Request Timeout (408)"
}

class InternalServerErrorException : IOException() {
    override val message: String
        get() = "Internal Server Error (500)"
}

class BadGatewayException : IOException() {
    override val message: String
        get() = "Bad Gateway (502)"
}

class GatewayTimeoutException : IOException() {
    override val message: String
        get() = "Gateway Timeout (504)"
}

class RateLimitExceededException : IOException() {
    override val message: String
        get() = "Rate Limit Exceeded (429)"
}

class ServiceUnavailableException : IOException() {
    override val message: String
        get() = "Service Unavailable (503)"
}

class ClientErrorException(private val statusCode: Int) : IOException() {
    override val message: String
        get() = "Client Error ($statusCode)"
}

class ServerErrorException(private val statusCode: Int) : IOException() {
    override val message: String
        get() = "Server Error ($statusCode)"
}