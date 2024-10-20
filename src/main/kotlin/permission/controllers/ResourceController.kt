package permission.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.WebRequest
import permission.dto.AddResource
import permission.dto.ResourceUser
import permission.dto.ResourceUserPermission
import permission.dto.ShareResource
import permission.exceptions.PermissionException
import permission.services.ResourceService

@RestController()
@RequestMapping("/resource")
class ResourceController(
    @Autowired
    private val service: ResourceService,
) {
    @ExceptionHandler(PermissionException::class)
    fun handleExceptions(
        ex: PermissionException,
        request: WebRequest,
    ): ResponseEntity<String> {
        println(ex.message + " | " + ex.status)
        return ResponseEntity(ex.message, ex.status)
    }

    @PostMapping("/create-resource")
    fun addResource(
        @RequestBody addResource: AddResource,
    ): ResponseEntity<ResourceUserPermission> = ResponseEntity(service.addResource(addResource), HttpStatus.CREATED)

    @GetMapping("/all-by-userId")
    fun getPermissionsForUser(
        @RequestParam id: String,
    ): ResponseEntity<List<ResourceUserPermission>> = ResponseEntity(service.findUserResources(id), HttpStatus.OK)

    @GetMapping("/user-resource")
    fun getSpecificPermission(
        @CookieValue("userId") userId: String,
        @CookieValue("resourceId") resourceId: String,
    ): ResponseEntity<ResourceUserPermission> = ResponseEntity(service.findByUsersIdAndResourceId(userId, resourceId), HttpStatus.OK)

    @PostMapping("/share-resource")
    fun shareResource(
        @RequestBody params: ShareResource,
    ): ResponseEntity<AddResource> {
        val resource = service.shareResource(params.selfId, params.otherId, params.resourceId, params.permissions)
        return ResponseEntity(resource, HttpStatus.CREATED)
    }

    @GetMapping("/can-write")
    fun checkCanWrite(
        @CookieValue("userId") userId: String,
        @CookieValue("resourceId") resourceId: String,
    ): ResponseEntity<ResourceUser> {
        val response = service.checkCanWrite(resourceId, userId)
        return ResponseEntity(response, HttpStatus.OK)
    }

    @GetMapping("/all-write-by-userId")
    fun getAllWriteableResourcesById(
        @RequestParam id: String,
    ): ResponseEntity<List<ResourceUserPermission>> {
        val response = service.getAllWriteableResources(id)
        return ResponseEntity(response, HttpStatus.OK)
    }

    @DeleteMapping("{resourceId}")
    fun deleteResource(
        @CookieValue("userId") userId: String,
        @PathVariable("resourceId") resourceId: String,
    ): ResponseEntity<String> {
        println("userId: $userId, resourceId: $resourceId")
        service.deleteResource(userId, resourceId)
        return ResponseEntity("Deleted Successfully", HttpStatus.OK)
    }
}
