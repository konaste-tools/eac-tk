import io.ktor.http.HttpStatusCode
import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalForeignApi::class)
fun main() = runBlocking {
    val shutdownSignal = CompletableDeferred<Unit>()
    val server = embeddedServer(CIO, port = 44444, host = "0.0.0.0") {
        routing {
            get("/tk/{tk}") {
                println(call.parameters["tk"])
                call.respond(HttpStatusCode.OK)
                shutdownSignal.complete(Unit)
            }
        }
    }
    launch {
        server.start(wait = true)
    }
    shutdownSignal.await()
    server.stop(gracePeriodMillis = 1000, timeoutMillis = 2000)
    return@runBlocking
}
