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
import org.slf4j.MDC
import java.util.UUID

class CorrelationIdFilterTests {
    private lateinit var filter: CorrelationIdFilter
    private lateinit var request: HttpServletRequest
    private lateinit var response: HttpServletResponse
    private lateinit var filterChain: FilterChain

    @BeforeEach
    fun setUp() {
        filter = CorrelationIdFilter()
        request = mock(HttpServletRequest::class.java)
        response = mock(HttpServletResponse::class.java)
        filterChain = mock(FilterChain::class.java)
        MDC.clear()
    }

    @Test
    fun testDoFilterInternalWithHeader() {
        val correlationId = UUID.randomUUID().toString()
        `when`(request.getHeader(CorrelationIdFilter.CORRELATION_ID_HEADER)).thenReturn(correlationId)
        `when`(response.status).thenReturn(200)

        filter.doFilterInternal(request, response, filterChain)

        verify(filterChain, times(1)).doFilter(request, response)
        assert(MDC.get(CorrelationIdFilter.CORRELATION_ID_KEY) == null)
    }

    @Test
    fun testDoFilterInternalWithoutHeader() {
        `when`(request.getHeader(CorrelationIdFilter.CORRELATION_ID_HEADER)).thenReturn(null)
        `when`(response.status).thenReturn(200)

        filter.doFilterInternal(request, response, filterChain)

        verify(filterChain, times(1)).doFilter(request, response)
        assert(MDC.get(CorrelationIdFilter.CORRELATION_ID_KEY) == null)
    }

    @Test
    fun testDoFilterInternalWithException() {
        val correlationId = UUID.randomUUID().toString()
        `when`(request.getHeader(CorrelationIdFilter.CORRELATION_ID_HEADER)).thenReturn(correlationId)
        `when`(response.status).thenReturn(500)
        doThrow(RuntimeException("Test Exception")).`when`(filterChain).doFilter(request, response)

        try {
            filter.doFilterInternal(request, response, filterChain)
        } catch (e: Exception) {
            // Expected exception
        }

        verify(filterChain, times(1)).doFilter(request, response)
        assert(MDC.get(CorrelationIdFilter.CORRELATION_ID_KEY) == null)
    }
}
