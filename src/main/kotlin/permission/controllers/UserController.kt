package permission.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import permission.entities.User
import permission.services.UserService

@RestController()
@RequestMapping("/user")
class UserController(
    @Autowired
    private val service: UserService,
) {
    @PostMapping("/create")
    fun createUser(
        @RequestParam id: String,
    ): ResponseEntity<User> {
        return ResponseEntity(service.findOrCreate(id), HttpStatus.CREATED)
    }

    @GetMapping()
    fun getAllUsers(): List<String> {
        return service.getAll()
    }
}
