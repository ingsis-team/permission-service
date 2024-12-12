package permission.services

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import permission.dto.ResourcePermissionCreateDTO
import permission.entities.Permission
import permission.entities.Resource
import permission.entities.User
import permission.repositories.ResourceRepository
import java.util.Optional

class DefaultResourceServiceTests {
    private lateinit var repository: ResourceRepository
    private lateinit var userService: UserService
    private lateinit var service: DefaultResourceService

    @BeforeEach
    fun setUp() {
        repository = mock(ResourceRepository::class.java)
        userService = mock(UserService::class.java)
        service = DefaultResourceService(repository, userService)
    }

    @Test
    fun testAddResource() {
        val resourcePermissionCreateDTO = ResourcePermissionCreateDTO("userId", "resource1", mutableListOf(Permission.READ))
        val resource = Resource(resourcePermissionCreateDTO)
        `when`(repository.save(any(Resource::class.java))).thenReturn(resource)
        `when`(userService.findOrCreate("userId")).thenReturn(User("userId"))

        val response = service.addResource(resourcePermissionCreateDTO)

        assertEquals("resource1", response.resourceId)
        assertFalse(response.permissions.contains(Permission.OWNER))
    }

    @Test
    fun testFindUserResources() {
        val resource = Resource(ResourcePermissionCreateDTO("userId", "resource1", mutableListOf(Permission.READ)))
        `when`(repository.findAllByUsersId("userId")).thenReturn(listOf(resource))

        val resources = service.findUserResources("userId")

        assertEquals(1, resources.size)
        assertEquals("resource1", resources[0].resourceId)
    }

    @Test
    fun testFindByUsersIdAndResourceId() {
        val resource = Resource(ResourcePermissionCreateDTO("userId", "resource1", mutableListOf(Permission.READ)))
        `when`(repository.findByResourceIdAndUsersId("resource1", "userId")).thenReturn(Optional.of(resource))

        val response = service.findByUsersIdAndResourceId("userId", "resource1")

        assertEquals("resource1", response.resourceId)
    }

    @Test
    fun testShareResource() {
        val resource = Resource(ResourcePermissionCreateDTO("userId", "resource1", mutableListOf(Permission.OWNER)))
        `when`(repository.findByResourceIdAndUsersId("resource1", "userId")).thenReturn(Optional.of(resource))
        `when`(repository.save(any(Resource::class.java))).thenReturn(resource)
        `when`(userService.findOrCreate("otherUserId")).thenReturn(User("otherUserId"))

        val sharedResource = service.shareResource("userId", "otherUserId", "resource1", mutableListOf(Permission.READ))

        assertEquals("resource1", sharedResource.resourceId)
    }

    @Test
    fun testGetAllWriteableResources() {
        val resource = Resource(ResourcePermissionCreateDTO("userId", "resource1", mutableListOf(Permission.WRITE)))
        `when`(repository.findAllByUsersId("userId")).thenReturn(listOf(resource))

        val resources = service.getAllWriteableResources("userId")

        assertEquals(1, resources.size)
        assertEquals("resource1", resources[0].resourceId)
    }

    @Test
    fun testDeleteResource() {
        val resource = Resource(ResourcePermissionCreateDTO("userId", "resource1", mutableListOf(Permission.WRITE)))
        `when`(repository.findByResourceId("resource1")).thenReturn(Optional.of(resource))
        `when`(repository.findAllByUsersId("userId")).thenReturn(listOf(resource))

        service.deleteResource("userId", "resource1")

        verify(repository, times(1)).delete(resource)
        `when`(repository.findByResourceId("resource1")).thenReturn(Optional.empty())
        assert(repository.findByResourceId("resource1").isEmpty)
    }
}
