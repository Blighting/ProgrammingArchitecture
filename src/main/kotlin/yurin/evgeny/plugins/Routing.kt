package yurin.evgeny.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import yurin.evgeny.entity.Message

fun Application.configureRouting() {
    routing {
        post("/sendMessage") {
            val message = call.receive<Message>()
            call.respondText(message.message, status = HttpStatusCode.OK)
        }
    }
}
