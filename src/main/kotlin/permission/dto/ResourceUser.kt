package permission.dto

import permission.entities.Permission

class ResourceUser(
    val userId: String,
    val resourceId: List<Permission>,
) {
    override fun toString(): String {
        return "ResourceUser(userId= '$userId', resourceId: '$resourceId'"
    }
}
