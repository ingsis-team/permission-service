package permission.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import permission.entities.User

@Repository
interface UserRepository : JpaRepository<User, String>
