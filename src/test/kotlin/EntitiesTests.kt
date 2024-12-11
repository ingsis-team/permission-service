package permission.dto
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import permission.entities.Permission
import permission.entities.Resource
import permission.entities.User

class EntitiesTests {
    @Test
    fun testResourceConstructorWithDTO() {
        val permissions = mutableListOf(Permission.READ, Permission.WRITE)
        val dto = ResourcePermissionCreateDTO("user1", "resource1", permissions)
        val resource = Resource(dto)

        assertEquals("resource1", resource.resourceId)
        assertEquals(1, resource.users.size)
        assertEquals("user1", resource.users[0].id)
        assertEquals(permissions.toSet(), resource.permissions)
    }

    @Test
    fun testResourceDefaultConstructor() {
        val resource = Resource()

        assertEquals("", resource.resourceId)
        assertEquals(0, resource.users.size)
        assertEquals(0, resource.permissions.size)
    }

    @Test
    fun testUserConstructor() {
        val user = User()

        assertEquals(0, user.resources.size)
    }
}
