package permission.filters

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.doThrow
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class RequestLogFilterTests {
    private lateinit var filter: RequestLogFilter
    private lateinit var request: HttpServletRequest
    private lateinit var response: HttpServletResponse
    private lateinit var filterChain: FilterChain

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(RequestLogFilterTests::class.java)
    }

    @BeforeEach
    fun setUp() {
        filter = RequestLogFilter()
        request = mock(HttpServletRequest::class.java)
        response = mock(HttpServletResponse::class.java)
        filterChain = mock(FilterChain::class.java)
    }

    @Test
    fun testDoFilterInternal() {
        `when`(request.requestURI).thenReturn("/test-uri")
        `when`(request.method).thenReturn("GET")
        `when`(response.status).thenReturn(200)

        filter.doFilterInternal(request, response, filterChain)

        verify(filterChain, times(1)).doFilter(request, response)
        verify(response, times(1)).status
    }

    @Test
    fun testDoFilterInternalWithException() {
        `when`(request.requestURI).thenReturn("/test-uri")
        `when`(request.method).thenReturn("GET")
        `when`(response.status).thenReturn(500)
        doThrow(RuntimeException("Test Exception")).`when`(filterChain).doFilter(request, response)

        try {
            filter.doFilterInternal(request, response, filterChain)
        } catch (e: Exception) {
            // Expected exception
        }

        verify(filterChain, times(1)).doFilter(request, response)
        verify(response, times(1)).status
    }
}
