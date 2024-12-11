package permission.dto

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import permission.entities.Permission

class DtoTests {
    @Test
    fun testPermissionResponse() {
        val permissions = setOf(Permission.READ, Permission.WRITE)
        val response = PermissionResponse("resource1", permissions)
        assertEquals("resource1", response.resourceId)
        assertEquals(permissions, response.permissions)
        assertEquals("PermissionResponse( resourceId = 'resource1', permissions = '$permissions')", response.toString())
    }

    @Test
    fun testResourcePermissionCreateDTO() {
        val permissions = mutableListOf(Permission.READ, Permission.WRITE)
        val dto = ResourcePermissionCreateDTO("user1", "resource1", permissions)
        assertEquals("user1", dto.userId)
        assertEquals("resource1", dto.resourceId)
        assertEquals(permissions, dto.permissions)
    }

    @Test
    fun testResourceUser() {
        val permissions = listOf(Permission.READ, Permission.WRITE)
        val user = ResourceUser("user1", permissions)
        assertEquals("user1", user.userId)
        assertEquals(permissions, user.resourceId)
        assertEquals("ResourceUser(userId= 'user1', resourceId: '$permissions'", user.toString())
    }

    @Test
    fun testShareResource() {
        val permissions = mutableListOf(Permission.READ, Permission.WRITE)
        val shareResource = ShareResource("self1", "other1", "resource1", permissions)
        assertEquals("self1", shareResource.selfId)
        assertEquals("other1", shareResource.otherId)
        assertEquals("resource1", shareResource.resourceId)
        assertEquals(permissions, shareResource.permissions)
    }
}
