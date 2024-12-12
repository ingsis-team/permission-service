package permission.services

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import permission.entities.User
import permission.repositories.UserRepository
import java.util.Optional

class DefaultUserServiceTests {
    private lateinit var userService: DefaultUserService
    private lateinit var userRepository: UserRepository

    @BeforeEach
    fun setUp() {
        userRepository = mock(UserRepository::class.java)
        userService = DefaultUserService(userRepository)
    }

    @Test
    fun testFindOrCreate_UserExists() {
        val userId = "existingUser"
        val user = User(userId)
        `when`(userRepository.findById(userId)).thenReturn(Optional.of(user))

        val result = userService.findOrCreate(userId)

        assertEquals(user, result)
        verify(userRepository, times(1)).findById(userId)
        verify(userRepository, never()).saveAndFlush(any(User::class.java))
    }

    @Test
    fun testFindOrCreate_UserDoesNotExist() {
        val userId = "newUser"
        `when`(userRepository.findById(userId)).thenReturn(Optional.empty())
        `when`(userRepository.saveAndFlush(any(User::class.java))).thenAnswer { it.getArgument(0) }

        val result = userService.findOrCreate(userId)

        assertEquals(userId, result.id)
        verify(userRepository, times(1)).findById(userId)
        verify(userRepository, times(1)).saveAndFlush(any(User::class.java))
    }

    @Test
    fun testGetAll() {
        val users = listOf(User("user1"), User("user2"))
        `when`(userRepository.findAll()).thenReturn(users)

        val result = userService.getAll()

        assertEquals(listOf("user1", "user2"), result)
        verify(userRepository, times(1)).findAll()
    }
}
