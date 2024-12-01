package permission.services

import permission.dto.PermissionResponse
import permission.dto.ResourcePermissionCreateDTO
import permission.dto.ResourceUser
import permission.entities.Permission

interface ResourceService {
    fun addResource(resourcePermissionCreateDTO: ResourcePermissionCreateDTO): PermissionResponse

    fun findUserResources(id: String): List<PermissionResponse>

    fun findByUsersIdAndResourceId(
        userId: String,
        resourceId: String,
    ): PermissionResponse

    fun shareResource(
        selfId: String,
        otherId: String,
        resourceId: String,
        permissions: MutableList<Permission>,
    ): ResourcePermissionCreateDTO

    fun checkCanWrite(
        resourceId: String,
        userId: String,
    ): ResourceUser

    fun getAllWriteableResources(userId: String): List<PermissionResponse>

    fun deleteResource(
        userId: String,
        resourceId: String,
    )
}
