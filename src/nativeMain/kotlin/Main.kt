import clients.DiscordWebhookClient
import clients.WebhookClient
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
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
fun main(args: Array<String>) = runBlocking {
    val shutdownSignal = CompletableDeferred<Unit>()
    val args = WebhookClient.buildContext(args)
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
    triggerWebhook(args)
    shutdownSignal.await()
    server.stop(gracePeriodMillis = 1000, timeoutMillis = 2000)
    return@runBlocking
}

suspend fun triggerWebhook(args: Map<String, String>) {
    if (!args.contains("-w")) return
    val client = HttpClient() {
        install(ContentNegotiation) {
            json()
        }
    }
    when (args["-w"]!!) {
        "discord" -> {
            DiscordWebhookClient(client).sendAvailableEvent(args)
        }
    }
}
