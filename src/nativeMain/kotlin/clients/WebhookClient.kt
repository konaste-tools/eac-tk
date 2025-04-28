package clients

interface WebhookClient {
    companion object {
         fun buildContext(args: Array<String>): Map<String, String> {
return args.toList().chunked(2).associate { it[0] to it[1] }
         }
    }

    suspend fun sendAvailableEvent(context: Map<String, String>)

}
