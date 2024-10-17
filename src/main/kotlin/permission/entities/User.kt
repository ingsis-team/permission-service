package permission.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.ManyToMany
import java.util.UUID

@Entity(name = "users")
class User(
    @Id
    val id: String = UUID.randomUUID().toString(),
    @ManyToMany(mappedBy = "users")
    @JsonIgnore
    val resources: MutableList<Resource> = mutableListOf(),
)
