package permission.dto

import permission.entities.Permission

class PermissionResponse(
    val resourceId: String,
    val permissions: Set<Permission>,
)
