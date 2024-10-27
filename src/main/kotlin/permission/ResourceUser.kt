package permission

import permission.entities.Permission

data class ResourceUser(
    val resourceId: String,
    val permissions: List<Permission>,
)
