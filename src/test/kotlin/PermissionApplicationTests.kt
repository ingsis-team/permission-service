package permission

import org.junit.jupiter.api.BeforeEach
import org.mockito.Mockito.mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.client.RestTemplate
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = [PermissionApplication::class])
@AutoConfigureMockMvc
@EnableWebMvc
class PermissionApplicationTests {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var restTemplate: RestTemplate

    @BeforeEach
    fun setUp() {
        val context: WebApplicationContext = mock(WebApplicationContext::class.java)
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build()
    }
}
