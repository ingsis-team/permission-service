package permission.resource

import permission.entities.Permission

class ShareResource(
    val selfId: String,
    val otherId: String,
    val resourceId: String,
    val permissions: MutableList<Permission>,
)
