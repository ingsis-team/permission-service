package permission.controllers

import org.slf4j.LoggerFactory
import org.slf4j.MDC
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
import permission.dto.PermissionResponse
import permission.dto.ResourcePermissionCreateDTO
import permission.dto.ResourceUser
import permission.dto.ShareResource
import permission.exceptions.PermissionException
import permission.filters.CorrelationIdFilter
import permission.services.DefaultResourceService

@RestController()
@RequestMapping("/resource")
class ResourceController(
    @Autowired
    private val service: DefaultResourceService,
) {
    private val logger = LoggerFactory.getLogger(ResourceController::class.java)

    @ExceptionHandler(PermissionException::class)
    fun handleExceptions(
        ex: PermissionException,
        request: WebRequest,
    ): ResponseEntity<String> {
        val correlationId = MDC.get(CorrelationIdFilter.CORRELATION_ID_KEY)
        logger.error("Exception occurred: ${ex.message} | Status: ${ex.status} | CorrelationId: $correlationId")
        return ResponseEntity(ex.message, ex.status)
    }

    @PostMapping("/create-resource")
    fun addResource(
        @RequestBody resourcePermissionCreateDTO: ResourcePermissionCreateDTO,
    ): ResponseEntity<Any> {
        val correlationId = MDC.get(CorrelationIdFilter.CORRELATION_ID_KEY)
        logger.info("Entering addResource with CorrelationId: $correlationId")
        return try {
            val result = service.addResource(resourcePermissionCreateDTO)
            logger.info("Exiting addResource with CorrelationId: $correlationId")
            ResponseEntity.ok(result)
        } catch (ex: Exception) {
            logger.error("Failed to add resource with CorrelationId: $correlationId", ex)
            throw PermissionException("Failed to add resource: ${ex.message}", HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/all-by-userId")
    fun getPermissionsForUser(
        @RequestParam id: String,
    ): ResponseEntity<List<PermissionResponse>> = ResponseEntity(service.findUserResources(id), HttpStatus.OK)

    @GetMapping("/user-resource")
    fun getSpecificPermission(
        @CookieValue("userId") userId: String,
        @CookieValue("outsideResourceId") resourceId: String,
    ): ResponseEntity<PermissionResponse> = ResponseEntity(service.findByUsersIdAndResourceId(userId, resourceId), HttpStatus.OK)

    @PostMapping("/share-resource")
    fun shareResource(
        @RequestBody params: ShareResource,
    ): ResponseEntity<ResourcePermissionCreateDTO> {
        val resource = service.shareResource(params.selfId, params.otherId, params.resourceId, params.permissions)
        return ResponseEntity(resource, HttpStatus.CREATED)
    }

    @GetMapping("/can-write")
    fun checkCanWrite(
        @CookieValue("userId") userId: String,
        @CookieValue("outsideResourceId") resourceId: String,
    ): ResponseEntity<ResourceUser> {
        val response = service.checkCanWrite(resourceId, userId)
        return ResponseEntity(response, HttpStatus.OK)
    }

    @GetMapping("/all-write-by-userId")
    fun getAllWriteableResourcesById(
        @RequestParam id: String,
    ): ResponseEntity<List<PermissionResponse>> {
        val response = service.getAllWriteableResources(id)
        return ResponseEntity(response, HttpStatus.OK)
    }

    @DeleteMapping("{resourceId}")
    fun deleteResource(
        @CookieValue("userId") userId: String,
        @PathVariable("resourceId") resourceId: String,
    ): ResponseEntity<String> {
        println("userId: $userId, outsideResourceId: $resourceId")
        service.deleteResource(userId, resourceId)
        return ResponseEntity("Deleted Successfully", HttpStatus.OK)
    }
}
