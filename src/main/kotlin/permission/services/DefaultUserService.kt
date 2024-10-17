package permission.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import permission.entities.User
import permission.repositories.UserRepository

@Service
class DefaultUserService(
    @Autowired
    val repository: UserRepository,
) : UserService {
    override fun findOrCreate(id: String): User {
        println("Searching for user with id: $id")
        val user =
            repository.findById(id).orElseGet {
                println("User not found, creating new user")
                createUser(id)
            }
        println("User found or created: $user")
        return user
    }

    override fun getAll(): List<String> {
        return repository.findAll().map { it.id }
    }

    private fun createUser(id: String): User {
        println("Creating user ...")
        return repository.saveAndFlush(User(id))
    }
}
