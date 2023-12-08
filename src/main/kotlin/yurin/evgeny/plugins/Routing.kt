package yurin.evgeny.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.html.body
import kotlinx.html.h1
import kotlinx.html.img
import yurin.evgeny.entity.Message
import java.io.File

fun Application.configureRouting() {
    routing {
        post("/sendMessage") {
            val message = call.receive<Message>()
            call.respondText("How dare you say ${message.message}", status = HttpStatusCode.OK)
        }
        get("/receiveImage") {
            val file = File("logo.png") // Replace with the path to your image file
            call.response.header(
                HttpHeaders.ContentType,
                "image/png"
            )
            call.respondFile(file)
        }
        staticResources("/image", "static")
    }
}
