package permission

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.web.client.RestTemplate

class AppConfigTest {
    @Test
    fun testRestTemplateBean() {
        val context = AnnotationConfigApplicationContext(AppConfig::class.java)
        val restTemplate = context.getBean(RestTemplate::class.java)
        assertNotNull(restTemplate)
    }
}
