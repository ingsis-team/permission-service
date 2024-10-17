package permission.services

import permission.entities.Permission
import permission.resource.AddResource
import permission.resource.ResourceUser
import permission.resource.ResourceUserPermission

interface ResourceService {
    fun addResource(addResource: AddResource): ResourceUserPermission

    fun findUserResources(id: String): List<ResourceUserPermission>

    fun findByUsersIdAndResourceId(
        userId: String,
        resourceId: String,
    ): ResourceUserPermission

    fun shareResource(
        selfId: String,
        otherId: String,
        resourceId: String,
        permissions: MutableList<Permission>,
    ): AddResource

    fun checkCanWrite(
        resourceId: String,
        userId: String,
    ): ResourceUser

    fun getAllWriteableResources(userId: String): List<ResourceUserPermission>

    fun deleteResource(
        userId: String,
        resourceId: String,
    )
}
