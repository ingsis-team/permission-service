package permission.dto

import permission.entities.Permission

class ResourcePermissionCreateDTO(
    val userId: String,
    val resourceId: String,
    val permissions: MutableList<Permission>,
)
