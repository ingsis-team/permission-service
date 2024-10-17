package permission.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import permission.entities.Permission
import permission.entities.Resource
import permission.exceptions.PermissionException
import permission.exceptions.ResourceNotFoundException
import permission.exceptions.UnauthorizedDeleteException
import permission.exceptions.UnauthorizedShareException
import permission.repositories.ResourceRepository
import permission.resource.AddResource
import permission.resource.ResourceUser
import permission.resource.ResourceUserPermission

@Service
class DefaultResourceService(
    @Autowired
    private val repository: ResourceRepository,
    @Autowired
    private val userService: UserService,
) : ResourceService {
    override fun addResource(addResource: AddResource): ResourceUserPermission {
        addResource.permissions.add(Permission.OWNER)
        return createResource(addResource)
    }

    override fun findUserResources(id: String): List<ResourceUserPermission> {
        val returnableResources = mutableListOf<ResourceUserPermission>()
        repository.findAllByUsersId(id).map {
            returnableResources.add(ResourceUserPermission(it.resourceId, it.permissions))
        }
        return returnableResources
    }

    override fun findByUsersIdAndResourceId(
        userId: String,
        resourceId: String,
    ): ResourceUserPermission {
        val resource =
            repository
                .findByResourceIdAndUsersId(resourceId, userId)
                .orElse(null)
        if (resource == null) {
            throw ResourceNotFoundException("The ids provided don't match an existing resource")
        }
        return ResourceUserPermission(resourceId, resource.permissions)
    }

    override fun shareResource(
        selfId: String,
        otherId: String,
        resourceId: String,
        permissions: MutableList<Permission>,
    ): AddResource {
        if (selfId == otherId) throw UnauthorizedShareException("Not allowed to share it with yourself")
        val resource = findByUsersIdAndResourceId(selfId, resourceId)
        if (!resource.permissions.contains(Permission.OWNER)) {
            throw UnauthorizedShareException("The User is not the owner of the resource and cannot share it")
        }
        val otherResources = findUserResources(otherId).filter { it.resourceId == resourceId }
        if (otherResources.isNotEmpty()) throw UnauthorizedShareException("User already has access to resource")
        val addResource = AddResource(otherId, resourceId, permissions)
        createResource(addResource)
        return addResource
    }

    override fun checkCanWrite(
        resourceId: String,
        userId: String,
    ): ResourceUser {
        val resources =
            getAllWriteableResources(userId).filter {
                it.resourceId == resourceId
            }
        if (resources.isEmpty()) throw PermissionException("User doesn't have that resource available", HttpStatus.UNAUTHORIZED)
        return ResourceUser(userId, resources[0].permissions.toList())
    }

    override fun getAllWriteableResources(userId: String): List<ResourceUserPermission> =
        findUserResources(userId).filter {
            it.permissions.contains(Permission.WRITE)
        }

    override fun deleteResource(
        userId: String,
        resourceId: String,
    ) {
        val canWrite = checkCanWrite(resourceId, userId).toString().contains(Permission.WRITE.toString())
        if (canWrite) {
            println("Authorized to delete\n deleting ...")
            println("resourceId: $resourceId")
            val resource =
                repository
                    .findByResourceId(
                        resourceId,
                    ).orElse(null) ?: throw ResourceNotFoundException("resource not found")
            repository.delete(resource)
        } else {
            println("Not authorized to delete")
            throw UnauthorizedDeleteException()
        }
    }

    private fun createResource(addResource: AddResource): ResourceUserPermission {
        println("ResourceId: ${addResource.resourceId}, userId: ${addResource.userId}")
        userService.findOrCreate(addResource.userId)
        val resource = repository.save(Resource(addResource))
        return ResourceUserPermission(resource.resourceId, resource.permissions)
    }
}
