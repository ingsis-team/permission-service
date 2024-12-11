package permission.controllers

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import permission.entities.User
import permission.services.UserService

class UserControllerTest {
    private val service = UserServiceImpl()
    private val controller = UserController(service)
/*
    @Test
    fun testCreateUser() {
        val userId = "user1"
        val user = User(userId)
        service.findOrCreate(userId) // Ensure the user is created in the service

        val response: ResponseEntity<User> = controller.createUser(userId)

        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertEquals(user, response.body)
    }

 */

    @Test
    fun testGetAllUsers() {
        val userId1 = "user1"
        val userId2 = "user2"
        service.findOrCreate(userId1)
        service.findOrCreate(userId2)

        val users: List<String> = controller.getAllUsers()

        assertEquals(listOf(userId1, userId2), users)
    }
}

class UserServiceImpl : UserService {
    private val users = mutableMapOf<String, User>()

    override fun findOrCreate(id: String): User {
        return users.computeIfAbsent(id) { User(it) }
    }

    override fun getAll(): List<String> {
        return users.keys.toList()
    }
}
