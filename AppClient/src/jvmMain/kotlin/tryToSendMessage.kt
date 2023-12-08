import entity.Message
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

suspend fun tryToSendMessage(message: String, client: HttpClient) {
    val response = client.post {
        url("http://127.0.0.1:8080/sendMessage")
        contentType(ContentType.Application.Json)
        setBody(Message(message))
    }
    println(response.bodyAsText())
    client.close()
}