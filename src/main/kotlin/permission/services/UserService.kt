package permission.services

import permission.entities.User

interface UserService {
    fun findOrCreate(id: String): User

    fun getAll(): List<String>
}
