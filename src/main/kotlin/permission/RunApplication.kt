package permission

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RunApplication

fun main(args: Array<String>) {
    runApplication<RunApplication>(*args)
}
