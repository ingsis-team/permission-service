package permission.dto

import permission.entities.Permission

class AddResource(
    val userId: String,
    val resourceId: String,
    val permissions: MutableList<Permission>,
)
