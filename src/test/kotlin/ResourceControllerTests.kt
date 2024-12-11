package permission.controllers

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.springframework.http.HttpStatus
import org.springframework.web.context.request.WebRequest
import permission.dto.PermissionResponse
import permission.dto.ResourcePermissionCreateDTO
import permission.dto.ResourceUser
import permission.dto.ShareResource
import permission.entities.Permission
import permission.exceptions.PermissionException
import permission.services.DefaultResourceService

class ResourceControllerTests {
    private val service = mock(DefaultResourceService::class.java)
    private val controller = ResourceController(service)
    private var list = MutableList(1) { Permission.READ }
    private var permissionList = setOf(Permission.READ)
/*
    @Test
    fun testAddResource() {
        val resourcePermissionCreateDTO = ResourcePermissionCreateDTO("userId", "resource1", list)
        val responseDTO = ResourcePermissionCreateDTO("userId", "resource1", list)

        val response = controller.addResource(resourcePermissionCreateDTO)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(responseDTO, response.body)
    }

 */

    @Test
    fun testGetPermissionsForUser() {
        val permissions = listOf(PermissionResponse("resource1", permissionList))

        `when`(service.findUserResources("user1")).thenReturn(permissions)

        val response = controller.getPermissionsForUser("user1")

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(permissions, response.body)
    }

    @Test
    fun testGetSpecificPermission() {
        val permissionResponse = PermissionResponse("resource1", permissionList)

        `when`(service.findByUsersIdAndResourceId("user1", "resource1")).thenReturn(permissionResponse)

        val response = controller.getSpecificPermission("user1", "resource1")

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(permissionResponse, response.body)
    }

    @Test
    fun testShareResource() {
        val shareResource = ShareResource("user1", "user2", "resource1", list)
        val responseDTO = ResourcePermissionCreateDTO("userId", "resource1", list)

        `when`(service.shareResource("user1", "user2", "resource1", list)).thenReturn(responseDTO)

        val response = controller.shareResource(shareResource)

        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertEquals(responseDTO, response.body)
    }

    @Test
    fun testCheckCanWrite() {
        val resourceUser = ResourceUser("resource1", listOf(Permission.WRITE))

        `when`(service.checkCanWrite("resource1", "user1")).thenReturn(resourceUser)

        val response = controller.checkCanWrite("user1", "resource1")

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(resourceUser, response.body)
    }

    @Test
    fun testGetAllWriteableResourcesById() {
        val permissions = listOf(PermissionResponse("resource1", permissionList))

        `when`(service.getAllWriteableResources("user1")).thenReturn(permissions)

        val response = controller.getAllWriteableResourcesById("user1")

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(permissions, response.body)
    }

    @Test
    fun testDeleteResource() {
        doNothing().`when`(service).deleteResource("user1", "resource1")

        val response = controller.deleteResource("user1", "resource1")

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals("Deleted Successfully", response.body)
    }

    @Test
    fun testHandleExceptions() {
        val exception = PermissionException("Test exception", HttpStatus.BAD_REQUEST)

        val response = controller.handleExceptions(exception, mock(WebRequest::class.java))

        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        assertEquals("Test exception", response.body)
    }
}
