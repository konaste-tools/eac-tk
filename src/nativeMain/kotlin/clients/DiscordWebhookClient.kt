package clients

import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType

class DiscordWebhookClient(
    private val client: HttpClient,
) : WebhookClient {
    companion object {
        private val ARGS = setOf(
            "-url",
            "-u",
        )
    }

    override suspend fun sendAvailableEvent(context: Map<String, String>) {
        if (!ARGS.all { it in context }) {
            return
        }
        client.post {
            url(context["-url"]!!)
            contentType(ContentType.Application.Json)
            setBody(mapOf("content" to "Hey <@${context["-u"]}>, your game is ready to launch!"))
        }
    }
}
