package permission.resource

import permission.entities.Permission

class ResourceUser(
    val userId: String,
    val resourceId: List<Permission>,
)
