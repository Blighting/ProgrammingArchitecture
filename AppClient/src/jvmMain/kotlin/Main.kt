import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.utils.io.*
import io.ktor.utils.io.core.*
import java.io.File

suspend fun main() {
    val client = HttpClient(CIO) {
        install(HttpRequestRetry) {
            retryIf(6) { _, response ->
                response.status.value.let { it in 500..599 }
            }
            delayMillis { retry -> retry * 2000L }
        }
        install(ContentNegotiation) {
            json()
        }
    }

    while (true) {
        val input = readLine()
        if (input?.contains("sendMessage") == true) {
            tryToSendMessage(input.split(" ")[1], client)
        } else {
            break
        }
    }
}

suspend fun downloadLogo(uri: String, client: HttpClient) {
    val file = File.createTempFile("logo", "png")
    client.prepareGet(Url(uri)).execute { httpResponse ->
        val channel: ByteReadChannel = httpResponse.body()
        while (!channel.isClosedForRead) {
            val packet = channel.readRemaining(DEFAULT_BUFFER_SIZE.toLong())
            while (!packet.isEmpty) {
                val bytes = packet.readBytes()
                file.appendBytes(bytes)
                println("Received ${file.length()} bytes from ${httpResponse.contentLength()}")
            }
        }
    }
}
