package permission

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.web.client.RestTemplate
import org.springframework.web.context.WebApplicationContext

@SpringBootTest(classes = [PermissionApplication::class])
class PermissionApplicationTest {
    @Autowired
    private lateinit var context: WebApplicationContext

    @MockBean
    private lateinit var restTemplate: RestTemplate

    private lateinit var mockMvc: MockMvc

    /*

    @Test
    fun testNoAuth() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build()

        val responseEntity = ResponseEntity("Mock response", HttpStatus.OK)
        `when`(restTemplate.exchange(
            eq("http://localhost:8081/"),
            eq(HttpMethod.GET),
            any(HttpEntity::class.java),
            eq(String::class.java)
        )).thenReturn(responseEntity)

        val result = mockMvc.perform(get("/")
            .header("Authorization", "Bearer mock-token"))
            .andExpect(status().isOk)
            .andReturn()

        assertEquals("Mock response", result.response.contentAsString)
    }

    @Test
    fun testNeedsAuth() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build()

        val result = mockMvc.perform(get("/needs-auth"))
            .andExpect(status().isOk)
            .andReturn()

        assertEquals("Great! you are authenticated", result.response.contentAsString)
    }

     */
}
