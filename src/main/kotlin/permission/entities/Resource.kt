@file:Suppress("ktlint:standard:no-wildcard-imports")

package permission.entities

import jakarta.persistence.*
import lombok.NoArgsConstructor
import lombok.Setter
import permission.resource.AddResource
import java.util.UUID

@Entity
@NoArgsConstructor
@Setter
class Resource(
    val resourceId: String,
    @ManyToMany
    @JoinTable(
        name = "resource_user",
        joinColumns = [JoinColumn(name = "resource_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")],
    )
    val users: MutableList<User>,
    @Enumerated(EnumType.STRING) @ElementCollection
    val permissions: MutableSet<Permission>,
) {
    @Id
    val id: String = UUID.randomUUID().toString()

    constructor(resource: AddResource) : this(
        resourceId = resource.resourceId,
        users = mutableListOf(User(resource.userId)),
        permissions = resource.permissions.toMutableSet(),
    ) {}

    constructor() : this(
        resourceId = "",
        users = mutableListOf(),
        permissions = mutableSetOf(),
    ) {}
}
