import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import permission.ResourceUser
import permission.entities.Permission

class ResourceUserTest {
    @Test
    fun testResourceUser() {
        val permissions = listOf(Permission.READ, Permission.WRITE)
        val resourceUser = ResourceUser(resourceId = "resource1", permissions = permissions)

        assertEquals("resource1", resourceUser.resourceId)
        assertEquals(permissions, resourceUser.permissions)
    }
}
