package com.neronguyen.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseToken
import io.ktor.http.auth.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

const val FIREBASE_AUTH = "FIREBASE_AUTH"
private const val FirebaseJWTAuthKey = "FirebaseAuth"
private const val FirebaseImplementationError =
    "Firebase  auth validate function is not specified, use firebase { validate { ... } } to fix this"

class FirebaseAuthProvider(config: FirebaseConfig) : AuthenticationProvider(config) {
    private val authHeader: (ApplicationCall) -> HttpAuthHeader? = config.authHeader
    private val authFunction = config.firebaseAuthenticationFunction

    override suspend fun onAuthenticate(context: AuthenticationContext) {
        val token = authHeader(context.call)
        if (token == null) {
            context.challengeInvalidCredentials()
            return
        }

        val principal = verifyFirebaseIdToken(context.call, token, authFunction)
        if (principal == null) {
            context.challengeInvalidCredentials()
            return
        }

        context.principal(principal)
    }
}

class FirebaseConfig(name: String?) : AuthenticationProvider.Config(name) {
    internal var authHeader: (ApplicationCall) -> HttpAuthHeader? =
        { call -> call.request.parseAuthorizationHeaderOrNull() }

    internal var firebaseAuthenticationFunction: AuthenticationFunction<FirebaseToken> = {
        throw NotImplementedError(FirebaseImplementationError)
    }

    fun validate(validate: suspend ApplicationCall.(FirebaseToken) -> User?) {
        firebaseAuthenticationFunction = validate
    }
}

fun AuthenticationConfig.firebase(
    name: String? = FIREBASE_AUTH,
    configure: FirebaseConfig.() -> Unit
) {
    val provider = FirebaseAuthProvider(FirebaseConfig(name).apply(configure))
    register(provider)
}

suspend fun verifyFirebaseIdToken(
    call: ApplicationCall,
    authHeader: HttpAuthHeader,
    tokenData: suspend ApplicationCall.(FirebaseToken) -> Principal?
): Principal? {
    if (authHeader.authScheme != "Bearer" || authHeader !is HttpAuthHeader.Single) {
        return null
    }

    val token: FirebaseToken = withContext(Dispatchers.IO) {
        FirebaseAuth.getInstance().verifyIdToken(authHeader.blob)
    }

    return tokenData(call, token)
}

fun AuthenticationContext.challengeInvalidCredentials() = challenge(
    FirebaseJWTAuthKey,
    AuthenticationFailedCause.InvalidCredentials
) { challengeFunc, call ->
    challengeFunc.complete()
    call.respond(UnauthorizedResponse(HttpAuthHeader.bearerAuthChallenge(realm = FIREBASE_AUTH)))
}


fun HttpAuthHeader.Companion.bearerAuthChallenge(realm: String): HttpAuthHeader =
    HttpAuthHeader.Parameterized("Bearer", mapOf(HttpAuthHeader.Parameters.Realm to realm))

fun ApplicationRequest.parseAuthorizationHeaderOrNull(): HttpAuthHeader? = try {
    parseAuthorizationHeader()
} catch (ex: IllegalArgumentException) {
    println("Failed to parse token")
    null
}
