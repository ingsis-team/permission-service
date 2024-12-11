package permission.services

import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class DefaultUserServiceTests {
    /*
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
    }

    @Test
    fun testGetAll() {
        val users = listOf(User("user1"), User("user2"))
        `when`(userRepository.findAll()).thenReturn(users)

        val result = userService.getAll()

        assertEquals(listOf("user1", "user2"), result)
        verify(userRepository, times(1)).findAll()
    }

     */
}
