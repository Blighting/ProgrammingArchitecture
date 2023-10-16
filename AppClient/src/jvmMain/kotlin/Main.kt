import entity.Message
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*

suspend fun main() {
    tryToGetResponse()
}

suspend fun tryToGetResponse() {
    val client = HttpClient(CIO) {
        install(HttpRequestRetry) {
            retryOnServerErrors(maxRetries = 6)
            delayMillis { retry ->
                retry * 10000L
            }
        }
        install(ContentNegotiation){
            json()
        }
    }
    val response = client.post {
        url("http://127.0.0.1:8080/sendMessage")
        contentType(ContentType.Application.Json)
        setBody(Message("Some message"))
    }
    println(response.bodyAsText())
    client.close()
}
