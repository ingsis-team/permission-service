package permission.exceptions

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

class ExceptionsTests {
    @Test
    fun testResourceNotFoundException() {
        val exception = ResourceNotFoundException("Resource not found")
        assertEquals("Resource not found", exception.message)
        assertEquals(HttpStatus.BAD_REQUEST, exception.status)
    }

    @Test
    fun testUnauthorizedDeleteException() {
        val exception = UnauthorizedDeleteException()
        assertEquals("User cannot delete this resource", exception.message)
        assertEquals(HttpStatus.UNAUTHORIZED, exception.status)
    }

    @Test
    fun testUnauthorizedShareException() {
        val exception = UnauthorizedShareException("Unauthorized share")
        assertEquals("Unauthorized share", exception.message)
        assertEquals(HttpStatus.BAD_REQUEST, exception.status)
    }
}
