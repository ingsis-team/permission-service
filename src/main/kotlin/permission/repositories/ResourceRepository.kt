package permission.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import permission.entities.Resource
import java.util.Optional

@Repository
interface ResourceRepository : JpaRepository<Resource, String> {
    fun findAllByUsersId(userId: String): List<Resource>

    fun findByResourceIdAndUsersId(
        outsideResourceId: String,
        userId: String,
    ): Optional<Resource>

    fun findByResourceId(outsideResourceId: String): Optional<Resource>
}
