package permission.dto

import permission.entities.Permission

class ResourceUserPermission(
    val resourceId: String,
    val permissions: Set<Permission>,
)
