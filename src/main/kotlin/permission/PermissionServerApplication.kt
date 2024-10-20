package permission

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate

@RestController
@SpringBootApplication
class PermissionServerApplication(
    @Autowired val template: RestTemplate,
) {
    @GetMapping("/")
    fun noAuth(
        @RequestHeader("Authorization") authorizationHeader: String,
    ): String {
        println(authorizationHeader)

        val headers = HttpHeaders()
        headers.set("Authorization", authorizationHeader)

        val entity = HttpEntity<String>(headers)

        val response: ResponseEntity<String> =
            template.exchange(
                "http://localhost:8081/",
                HttpMethod.GET,
                entity,
                String::class.java,
            )

        return response.body ?: "No response"
    }

    @GetMapping("/needs-auth")
    fun needsAuth(): String {
        return "Great! you are authenticated"
    }
}

fun main(args: Array<String>) {
    runApplication<PermissionServerApplication>(*args)
}
